package cube.logic.parser;

import cube.logic.command.ConfigCommand;
import cube.logic.command.ConfigCommand.ConfigType;
import cube.logic.parser.exception.ParserErrorMessage;
import cube.logic.parser.exception.ParserException;
import cube.storage.config.UiConfig;
import cube.storage.config.LogConfig;

public class ConfigCommandParser implements ParserPrototype<ConfigCommand> {

    public ConfigCommand parse(String[] args) throws ParserException {
        String[] params = new String[]{"-h","-w","-s","-c","-l"};

        if(ParserUtil.hasInvalidParameters(args,params)){
            throw new ParserException(ParserErrorMessage.INVALID_PARAMETER);
        }
        if(ParserUtil.hasRepetitiveParameters(args)){
            throw new ParserException(ParserErrorMessage.REPETITIVE_PARAMETER);
        }
        if (args.length == 1) {
            return new ConfigCommand(ConfigType.VIEW);
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
            case LOG:
                int maxFileCountIndex = -1;
                int maxFileSizeIndex = -1;
                int currentLogLevelIndex = -1;
                for (int i = 1; i < args.length; i ++) {
                    if (args[i].equals("-c")) {
                        maxFileCountIndex = i;
                    }
                    if (args[i].equals("-s")) {
                        maxFileSizeIndex = i;
                    }
                    if (args[i].equals("-l")) {
                        currentLogLevelIndex = i;
                    }
                }

                LogConfig logConfig = new LogConfig();
                if (maxFileCountIndex != -1) {
                    if(!ParserUtil.isValidNumber(args[maxFileCountIndex + 1])){
                        throw new ParserException(ParserErrorMessage.INVALID_NUMBER);
                    }
                    logConfig.setMaxFileCount(Integer.parseInt(args[maxFileCountIndex + 1]));
                }
                if (maxFileSizeIndex != -1) {
                    if(!ParserUtil.isValidNumber(args[maxFileSizeIndex + 1])){
                        throw new ParserException(ParserErrorMessage.INVALID_NUMBER);
                    }
                    logConfig.setMaxFileSizeMB(Integer.parseInt(args[maxFileSizeIndex + 1]));
                }
                if (currentLogLevelIndex != -1) {
                    logConfig.setCurrentLogLevel(args[currentLogLevelIndex + 1]);
                }
                return new ConfigCommand(configType, logConfig);
            case VIEW:
                return new ConfigCommand(configType);
        }

        return null;
    }
}
