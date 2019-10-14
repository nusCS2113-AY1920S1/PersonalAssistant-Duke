package Events.Storage;

import Events.EventTypes.Event;

public class ClashException extends Exception{
        private Event clashEvent;

        public ClashException(Event clashEvent) {
                this.clashEvent = clashEvent;
        }

        public Event getClashEvent(){
                return clashEvent;
        }
}
