package core;

public class CommandFactory {
    public CommandResponse executeCommandByRequest(CommandRequest request, Manager manager, Invoker invoker){
        CommandResponse response = switch (request.getName()){
            case "add"-> invoker.add.execute(manager, request.getArgs()); // +
            case "add_if_max" -> invoker.addIfMax.execute(manager, request.getArgs()); // +
            case "add_if_min" -> invoker.addIfMin.execute(manager, request.getArgs()); // +
            case "clear" -> invoker.clear.execute(manager, request.getArgs()); // +
            case "filter_greater_than_price" -> invoker.filterGreaterThanPrice.execute(manager, request.getArgs()); // +
            case "help" -> invoker.help.execute(manager, request.getArgs()); // +
            case "history" -> invoker.history.execute(manager, request.getArgs()); // +
            case "info" -> invoker.info.execute(manager, request.getArgs()); // +
            case "print_field_ascending_price" -> invoker.printFieldAscendingPrice.execute(manager, request.getArgs()); // +
            case "print_field_descending_refundable" -> invoker.printFieldDescendingRefundable.execute(manager, request.getArgs()); // +
            case "remove_by_id" -> invoker.removeById.execute(manager, request.getArgs()); // +
            case "show" -> invoker.show.execute(manager, request.getArgs()); // +
            case "update" -> invoker.updateId.execute(manager, request.getArgs()); // +
            default -> new CommandResponse("Команда не распознана");
        };
        manager.updateHistory(request.getName());
        return response;
    }
}
