package cube.logic.parser;

import cube.logic.command.ConfigCommand;
import cube.logic.command.ConfigCommand.ConfigType;
import cube.logic.parser.exception.ParserErrorMessage;
import cube.logic.parser.exception.ParserException;
import cube.storage.config.UiConfig;

public class ConfigCommandParser implements ParserPrototype<ConfigCommand> {

    public ConfigCommand parse(String[] args) throws ParserException {
        if (args.length < 3) {
            throw new ParserException(ParserErrorMessage.NOT_ENOUGH_PARAMETER);
        }

        int configTypeIndex = 1;

        ConfigType configType = ConfigType.valueOf(args[configTypeIndex]);

        switch (configType) {
            case UI:
                int heightIndex = -1;
                int widthIndex = -1;
                for (int i = 1; i < args.length; i ++) {
                    if (args[i].equals("-h")) {
                        heightIndex = i;
                    }
                    if (args[i].equals("-w")) {
                        widthIndex = i;
                    }
                }

                UiConfig uiConfig = new UiConfig();
                if (heightIndex != -1) {
                    uiConfig.setWindowHeight(Double.parseDouble(args[heightIndex + 1]));
                }
                if (widthIndex != -1) {
                    uiConfig.setWindowWidth(Double.parseDouble(args[widthIndex + 1]));
                }
                return new ConfigCommand(configType, uiConfig);
        }

        return null;
    }
}
