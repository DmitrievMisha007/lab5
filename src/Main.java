import core.App;
import core.Collection;
import core.Invoker;

public class Main{
    public static void main(String[] args) {
        if (args.length == 1){
            Collection collection = new Collection();
            collection.readCollection(args[0]);
            Invoker invoker = new Invoker();
            invoker.init();
            App.init(collection, args[0], invoker);
            App.run();
            return;
        }
        Collection collection = new Collection();
        collection.readCollection("data.json");
        Invoker invoker = new Invoker();
        invoker.init();
        App.init(collection, "data.json", invoker);
        App.run();
    }
}
