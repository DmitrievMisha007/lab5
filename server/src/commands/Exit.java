package commands;

import core.App;
import core.Manager;
import core.CommandResponse;
import interfases.Command;

import java.util.Map;

/**
 * Команда завершения цикла программы (без сохранения файла).
 */
public class Exit implements Command {
    /**
     * Вызывает команду
     *
     * @return Ответ коиенту
     */
    @Override
    public CommandResponse execute(Manager manager, Map<String, Object> args){
        // Сохраняем коллекцию перед выходом
        manager.writeCollection();
        // Останавливаем сервер
        App.stop();
        // Возвращаем сообщение (клиент может его получить до завершения)
        return new CommandResponse("Сервер завершает работу. Коллекция сохранена.");
    }
}
