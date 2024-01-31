package ru.ylab.configs;

import org.reflections.Reflections;

import java.util.Map;
import java.util.Set;

/**
 * Класс {@code JavaConfig} реализует интерфейс {@link Config} и предоставляет конфигурацию приложения на основе Java-кода.
 * <p>
 * Для конфигурации требуется указать пакет для сканирования и соответствие интерфейсов их реализациям.
 * </p>
 * <p>
 * Для использования создайте экземпляр класса, передав пакет для сканирования и маппинг интерфейсов на их реализации.
 * </p>
 * <p>
 * Обратите внимание, что для каждого интерфейса должна быть ровно одна реализация; в противном случае, будет выброшено исключение.
 * </p>
 *
 * @author Pesternikov Danil
 */
public class JavaConfig implements Config {

    /**
     * Сканер для поиска классов в указанном пакете.
     */
    private final Reflections scanner;

    /**
     * Соответствие интерфейсов и их реализаций.
     */
    private final Map<Class, Class> ifcToImplClass;

    /**
     * Создает экземпляр {@code JavaConfig} с указанным пакетом для сканирования и маппингом интерфейсов на их реализации.
     *
     * @param packageToScan   Пакет для сканирования классов.
     * @param ifcToImplClass  Соответствие интерфейсов и их реализаций.
     */
    public JavaConfig(String packageToScan, Map<Class, Class> ifcToImplClass) {
        this.ifcToImplClass = ifcToImplClass;
        this.scanner = new Reflections(packageToScan);
    }

    /**
     * Возвращает класс реализации для указанного интерфейса.
     * Если реализация уже задана в маппинге, она возвращается; в противном случае, производится поиск реализации в сканированном пакете.
     *
     * @param ifc интерфейс, для которого нужно получить реализацию.
     * @param <T> Тип интерфейса.
     * @return Класс реализации интерфейса.
     * @throws RuntimeException Если количество реализаций интерфейса не равно 1.
     */
    @Override
    public <T> Class<? extends T> getImplClass(Class<T> ifc) {
        return ifcToImplClass.computeIfAbsent(ifc, aClass -> {
            Set<Class<? extends T>> classes = scanner.getSubTypesOf(ifc);

            if (classes.size() != 1) {
                throw new RuntimeException(ifc + "Количество имплиментаций 0 или больше 1");
            }

            return classes.iterator().next();
        });
    }

    /**
     * Возвращает объект {@link Reflections}, предоставляющий доступ к результатам сканирования классов.
     *
     * @return Объект Reflections.
     */
    @Override
    public Reflections getScanner() {
        return scanner;
    }

}
