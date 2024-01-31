package ru.ylab.adapters.in.view;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ylab.annotations.Autowired;
import ru.ylab.annotations.Init;
import ru.ylab.application.exception.*;
import ru.ylab.application.in.*;
import ru.ylab.application.model.LoginModel;
import ru.ylab.application.model.RegisterModel;
import ru.ylab.application.model.UtilityMeterModel;
import ru.ylab.domain.model.Role;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class Menu {

    private String name;

    private List<MenuItem> menuItems;

    @Autowired
    private LoginUser loginUser;

    @Autowired
    private RegisterUser registerUser;

    @Autowired
    private SubmitUtilityMeter submitUtilityMeter;

    @Autowired
    private GetRoleCurrentUser getRoleCurrentUser;

    @Autowired
    private GetAllUtilityMeter getAllUtilityMeter;

    @Autowired
    private GetUtilityMeter getUtilityMeter;

    @Autowired
    private GetUtilityMeterByMonth getUtilityMeterByMonth;

    @Autowired
    private GetUtilityMeterTypes getUtilityMeterTypes;

    @Autowired
    private GetAuditInfo getAuditInfo;
    @Autowired
    private AddNewMeterType addNewMeterType;

    @Autowired
    private LogoutUser logoutUser;

    private Menu roleMenu;

    public Menu(String name) {
        this.name = name;
    }

    public Menu(String name, List<MenuItem> menuItems) {
        this.name = name;
        this.menuItems = menuItems;
    }

    @Init
    public void init() {
        roleMenu = new Menu("МЕНЮ");
        this.name = "ВХОД";
        this.menuItems = List.of(
                MenuItem.builder()
                        .title("0. <--- Выход")
                        .action(() -> System.out.println("Программа завершена"))
                        .build(),
                MenuItem.builder()
                        .title("1. <--- Авторизация")
                        .action(() -> {
                            try {
                                Scanner scanner = new Scanner(System.in);
                                System.out.println("Введите имя пользователя");
                                var username = scanner.nextLine();
                                System.out.println("Введите пароль");
                                var password = new Scanner(System.in).nextLine();
                                loginUser.execute(new LoginModel(username, password));
                                if (getRoleCurrentUser.execute() == Role.USER) {
                                    setupUserMenu();
                                } else {
                                    setupAdminMenu();
                                }
                            } catch (UserNotFoundException | IncorrectPasswordException exception) {
                                System.out.println(exception.getMessage());
                                setupDefaultMenu();
                            }

                        })
                        .nextMenu(roleMenu).build(),
                MenuItem.builder()
                        .title("2. <--- Регистрация")
                        .action(() -> {
                            try {
                                Scanner scanner = new Scanner(System.in);
                                System.out.println("Введите имя пользователя(5-15 символов, eng)");
                                var username = scanner.nextLine();
                                System.out.println("Введите пароль(5-15 символов, eng)");
                                var password = scanner.nextLine();
                                registerUser.execute(new RegisterModel(username, password));
                            } catch (UsernameAlreadyExistsException | NotValidUsernameOrPasswordException exception) {
                                System.out.println(exception.getMessage());
                            }
                        })
                        .build()
        );
    }

    private void setupDefaultMenu() {
        roleMenu.menuItems = this.menuItems;
    }

    private void setupUserMenu() {
        roleMenu.menuItems = List.of(
                MenuItem.builder()
                        .title("0. <--- Выход")
                        .action(() -> logoutUser.execute())
                        .build(),
                MenuItem.builder()
                        .title("1. <--- Получить актуальные показания")
                        .action(() -> getUtilityMeter.execute().forEach(System.out::println))
                        .build(),
                MenuItem.builder().title("2. <--- Подать показания")
                        .action(() -> {
                            try {
                                Scanner scanner = new Scanner(System.in);
                                var meterTypes = getUtilityMeterTypes.execute();
                                Map<String, Double> utilityMeter = new HashMap<>();
                                for (var type : meterTypes) {
                                    System.out.println("Введите показание счетчика " + type.getName() + ":");
                                    var counter = scanner.nextDouble();
                                    System.out.println(type.getName() + ": " + counter);
                                    utilityMeter.put(type.getName(), counter);
                                }
                                submitUtilityMeter.execute(utilityMeter);
                            } catch (MonthlySubmitLimitException e) {
                                System.out.println(e.getMessage());
                            }
                        })
                        .build(),
                MenuItem.builder().title("3. <--- Показать показания за конкретный месяц")
                        .action(() -> {
                            Scanner scanner = new Scanner(System.in);
                            System.out.println("Введите месяц");
                            var month = scanner.nextInt();
                            if (month >= 1 && month <= 12) {
                                getUtilityMeterByMonth.execute(month).forEach(System.out::println);
                            }
                        })
                        .build(),
                MenuItem.builder().title("4. <--- Показать историю показаний")
                        .action(() -> {
                            Map<LocalDate, List<UtilityMeterModel>> groupedByDate = getAllUtilityMeter.execute().stream()
                                    .collect(Collectors.groupingBy(UtilityMeterModel::readingsDate));
                            groupedByDate.forEach((date, models) -> {
                                System.out.println("Дата: " + date);
                                models.forEach(System.out::println);
                            });
                        })
                        .build()
        );
    }

    private void setupAdminMenu() {
        roleMenu.menuItems = List.of(
                MenuItem.builder()
                        .title("0. <--- Выход")
                        .action(() -> logoutUser.execute())
                        .build(),
                MenuItem.builder()
                        .title("1. <--- Просмотр аудита действий")
                        .action(() -> getAuditInfo.execute().forEach(System.out::println))
                        .build(),
                MenuItem.builder()
                        .title("2. <--- Просмотреть все показания")
                        .action(() -> {
                            Map<String, List<UtilityMeterModel>> groupedByDate = getAllUtilityMeter.execute().stream()
                                    .collect(Collectors.groupingBy(UtilityMeterModel::username));
                            groupedByDate.forEach((username, models) -> {
                                System.out.println("Username: " + username);
                                models.forEach(System.out::println);
                            });
                        })
                        .build(),
                MenuItem.builder()
                        .title("3. <--- Добавить новый тип показания")
                        .action(() -> {
                            Scanner scanner = new Scanner(System.in);
                            var typeName = scanner.nextLine();
                            addNewMeterType.execute(typeName);
                        })
                        .build()
        );
    }

    @Override
    public String toString() {
        return "----------" + name + "----------\n" +
                menuItems.toString().replaceAll(", |]|\\[", "");
    }
}