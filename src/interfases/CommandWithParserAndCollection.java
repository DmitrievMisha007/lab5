package interfases;

import core.Collection;
import core.Parser;

public interface CommandWithParserAndCollection {
    public void execute(Collection collection, Parser parser);
}
