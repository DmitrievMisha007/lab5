import core.App;
import core.Collection;
import core.Invoker;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Main{
    private void writeFile(String fileName)throws Exception{
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        writer.close();
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 1){
            return;
        }
        Collection collection = new Collection();
        collection.readCollection("data.json");
        Invoker invoker = new Invoker();
        invoker.init();
//        invoker.show.execute(collection);
        App.init(collection, args[0], invoker);
        invoker.printFieldAscendingPrice.execute();
        invoker.printFieldDescendingRefundable.execute();
    }
}
