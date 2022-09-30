package com.moneyguard.moneyguard.service;

import com.moneyguard.moneyguard.model.Guard;
import com.moneyguard.moneyguard.model.ImportanceLevel;
import com.moneyguard.moneyguard.model.SavingGoal;
import com.moneyguard.moneyguard.model.User;
import com.moneyguard.moneyguard.repository.*;
import com.moneyguard.moneyguard.request.DashboardRequest;
import com.moneyguard.moneyguard.request.RetrieveTransactionsRequest;
import com.moneyguard.moneyguard.request.UpdateAccountRequest;
import com.moneyguard.moneyguard.resource.ChartDataElement;
import com.moneyguard.moneyguard.resource.PieChartData;
import com.moneyguard.moneyguard.resource.Targetable;
import com.moneyguard.moneyguard.response.DashboardResponse;
import com.moneyguard.moneyguard.response.DropdownListResource;
import com.moneyguard.moneyguard.response.UserDetailResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    ImportanceLevelRepository importanceLevelRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    GuardRepository guardRepository;

    @Autowired
    SavingGoalRepository savingGoalRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ImportanceLevelService importanceLevelService;

    @Autowired
    GuardService guardService;

    @Autowired
    SavingGoalService savingGoalService;

    @Autowired
    PasswordEncoder encoder;

    public Set<ImportanceLevel> generateDefaultImportanceLevels(User user) {
        Set<ImportanceLevel> importanceLevels = new HashSet<>();
        String[] defaultValues = new String[]{"Low", "Medium", "High"};
        short level = 1;
        for (String element : defaultValues) {
            ImportanceLevel importanceLevel = new ImportanceLevel();
            importanceLevel.setUser(user);
            importanceLevel.setName(element);
            importanceLevel.setLevel(level);
            importanceLevel.setCreatedAt(new Date());
            importanceLevel.setUpdatedAt(new Date());
            level += 5;
            importanceLevelRepository.save(importanceLevel);
            importanceLevels.add(importanceLevel);
        }
        return importanceLevels;
    }

    @Override
    public DashboardResponse getDashboard(User user, DashboardRequest request) throws ParseException {
        DashboardResponse response = new DashboardResponse();
        response.setUser(new UserDetailResponse(user));

        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        Date oneMonthAgo = cal.getTime();

        long DAY_IN_MS = 1000 * 60 * 60 * 24;
        Date oneWeekAgo = new Date(System.currentTimeMillis() - (7 * DAY_IN_MS));

        Double weekIn = transactionRepository.getTotal(new RetrieveTransactionsRequest(
                0,
                "",
                new String[]{},
                new String[]{},
                new String[]{},
                new String[]{"1"},
                oneWeekAgo,
                now,
                "name",
                "ASC"
        ));
        response.setWeekTotalIn(weekIn != null ? weekIn : Double.valueOf(0));

        Double monthIn = transactionRepository.getTotal(new RetrieveTransactionsRequest(
                0,
                "",
                new String[]{},
                new String[]{},
                new String[]{},
                new String[]{"1"},
                oneMonthAgo,
                now,
                "name",
                "ASC"
        ));
        response.setMonthTotalIn(monthIn != null ? monthIn : Double.valueOf(0));

        Double weekOut = transactionRepository.getTotal(new RetrieveTransactionsRequest(
                0,
                "",
                new String[]{},
                new String[]{},
                new String[]{},
                new String[]{"2"},
                oneWeekAgo,
                now,
                "name",
                "ASC"
        ));
        response.setWeekTotalOut(weekOut != null ? weekOut : Double.valueOf(0));

        Double monthOut = transactionRepository.getTotal(new RetrieveTransactionsRequest(
                0,
                "",
                new String[]{},
                new String[]{},
                new String[]{},
                new String[]{"2"},
                oneMonthAgo,
                now,
                "name",
                "ASC"
        ));
        response.setMonthTotalOut(monthOut != null ? monthOut : Double.valueOf(0));

        Double weekSaving = transactionRepository.getTotal(new RetrieveTransactionsRequest(
                0,
                "",
                new String[]{},
                new String[]{},
                new String[]{},
                new String[]{"3"},
                oneWeekAgo,
                now,
                "name",
                "ASC"
        ));
        response.setWeekTotalSaving(weekSaving != null ? weekSaving : Double.valueOf(0));

        Double monthSaving = transactionRepository.getTotal(new RetrieveTransactionsRequest(
                0,
                "",
                new String[]{},
                new String[]{},
                new String[]{},
                new String[]{"3"},
                oneMonthAgo,
                now,
                "name",
                "ASC"
        ));
        response.setMonthTotalSaving(monthSaving != null ? monthSaving : Double.valueOf(0));

        // Chart

        List<DropdownListResource> importanceLevels = importanceLevelService.getSelectData("");
        Set<ChartDataElement> chartDataElements = new HashSet<>();
        PieChartData pieChartData = new PieChartData();
        Double totalValue = (double) 0;
        for (DropdownListResource importanceLevel:importanceLevels) {
            Double importanceLevelTotal = transactionRepository.getTotal(new RetrieveTransactionsRequest(
                    0,
                    "",
                    new String[]{},
                    new String[]{},
                    new String[]{importanceLevel.getValue()},
                    new String[]{"2"},
                    oneMonthAgo,
                    now,
                    "name",
                    "ASC"
            ));
            ChartDataElement element = new ChartDataElement();
            element.setName(importanceLevel.getText());
            element.setValue(importanceLevelTotal);
            chartDataElements.add(element);
            totalValue += importanceLevelTotal != null ? importanceLevelTotal : 0;
        }

        for (ChartDataElement chartDataElement: chartDataElements) {
            double percentage = 0;
            if (totalValue != null && totalValue != (double) 0 && chartDataElement.getValue() != null && chartDataElement.getValue() != (double) 0) {
                percentage = (chartDataElement.getValue() / totalValue) * 100;
            }
            chartDataElement.setPercentage((float) percentage);
        }

        pieChartData.setElements(chartDataElements);
        pieChartData.setTotalValue(totalValue);
        response.setImportanceBreakdownChartData(pieChartData);

        Set<Guard> userGuards = guardRepository.findAllByUserId(user.getId());
        Set<Targetable> guards = new HashSet<>();
        for (Guard guard:userGuards) {
            guards.add(guardService.getCurrentState(guard));
        }

        Set<SavingGoal> userSavingGoals = savingGoalRepository.findAllByUserId(user.getId());
        Set<Targetable> savingGoals = new HashSet<>();
        for (SavingGoal savingGoal:userSavingGoals) {
            savingGoals.add(savingGoalService.getCurrentState(savingGoal));
        }

        response.setSavingGoals(savingGoals);
        response.setGuards(guards);
        return response;
    }

    @Override
    public UserDetailResponse updateAccount(UpdateAccountRequest request, User user) {
        user.setCurrencySymbol("£");
        user.setCurrencyPlacement(true);
        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        if (request.getPrefix() != null) {
            user.setPrefix(request.getPrefix());
        } else {
            user.setPrefix("");
        }
        if (request.getCurrencyPlacement() != null) {
            user.setCurrencyPlacement(request.getCurrencyPlacement());
        }
        if (request.getCurrencySymbol() != null) {
            user.setCurrencySymbol(request.getCurrencySymbol());
        }

        user.setName(user.getPrefix()+user.getFirstName()+" "+user.getLastName());
        if (request.getPassword() != null) {
            user.setPassword(encoder.encode(request.getPassword()));
        }
        user.setUpdatedAt(new Date());
        userRepository.save(user);
        return new UserDetailResponse(user);
    }
}
