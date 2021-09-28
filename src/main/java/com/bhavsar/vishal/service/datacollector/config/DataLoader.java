package com.bhavsar.vishal.service.datacollector.config;

import com.bhavsar.vishal.service.datacollector.db.entity.expense.CategoryRecord;
import com.bhavsar.vishal.service.datacollector.db.entity.expense.ExpenseRecord;
import com.bhavsar.vishal.service.datacollector.db.entity.user.Role;
import com.bhavsar.vishal.service.datacollector.db.entity.user.User;
import com.bhavsar.vishal.service.datacollector.model.login.ERole;
import com.bhavsar.vishal.service.datacollector.security.services.ExpenseCategoryService;
import com.bhavsar.vishal.service.datacollector.security.services.ExpenseService;
import com.bhavsar.vishal.service.datacollector.security.services.RoleService;
import com.bhavsar.vishal.service.datacollector.security.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Log4j2
@Component
public class DataLoader implements ApplicationRunner {
    boolean isSetup = false;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ExpenseCategoryService expenseCategoryService;

    @Autowired
    private ExpenseService expenseService;

    @Override
    public void run(final ApplicationArguments args) {
        if (isSetup) {
            return;
        }
        Arrays.stream(ERole.values()).forEach(this::loadRolesInDB);
        final User user = loadUsers();
        final List<CategoryRecord> categoryRecords = loadCategories(user);
        loadExpenseData(user, categoryRecords);
        isSetup = true;
    }

    private List<ExpenseRecord> loadExpenseData(final User user, final List<CategoryRecord> categoryRecords) {
        try {
            return categoryRecords.stream()
                    .map(c -> ExpenseRecord.builder()
                            .expenseDate(new Date())
                            .categoryRecord(c)
                            .deleted(false)
                            .expenseAmount(new Random().nextDouble()*100)
                            .description("Test expense for " + c.getName())
                            .user(user)
                            .build())
                    .map(e -> expenseService.save(e))
                    .collect(Collectors.toList());
        } catch (final Exception e) {
            if (e instanceof DataIntegrityViolationException) {
                log.warn("Expense records already loaded in db. Won't load again.");
            } else {
                log.error("Failed to load expense records in db. Please add manually.");
            }
        }
        return null;
    }

    private List<CategoryRecord> loadCategories(final User user) {
        final List<String> categoryNames = Arrays.asList("Rent", "Fuel", "Utilities", "Internet", "Phone");
        try {
            return categoryNames.stream()
                    .map(s -> CategoryRecord.builder().name(s).user(user).build())
                    .map(r -> expenseCategoryService.save(r))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            if (e instanceof DataIntegrityViolationException) {
                log.warn("Categories already loaded in db. Won't load again.");
                return expenseCategoryService.findAll(Sort.by(Sort.Order.asc("name")));
            } else {
                log.error("Failed to load categories in db. Please add manually.");
            }
        }
        return null;
    }

    private User loadUsers() {
        try {
            log.debug("Saving test user in db.");
            final User user = User.builder()
                    .name("Test User").email("vishubhavsarautomation+admin@gmail.com")
                    .password(passwordEncoder.encode("password"))
                    .username("admin").enabled(true).using2FA(false).build();
            return userService.save(user);
        } catch (Exception e) {
            if (e instanceof DataIntegrityViolationException) {
                log.warn("User already loaded in db. Won't load again.");
                return userService.findByUsername("admin").get();
            } else {
                log.error("Failed to load Test User in db. Please sign up manually.");
            }
        }
        return null;
    }

    private Role loadRolesInDB(final ERole eRole) {
        final var roleByName = roleService.findByName(eRole);
        if (roleByName.isPresent()) {
            log.info("Role [{}] is already loaded in db. Won't load again.", eRole.name());
            return null;
        }
        final var role = Role.builder().name(eRole).build();
        log.debug("Loading role [{}] in db.", eRole.name());
        return roleService.save(role);
    }
}
