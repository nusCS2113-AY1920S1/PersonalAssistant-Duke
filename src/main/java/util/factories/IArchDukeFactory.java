package util.factories;

public interface IArchDukeFactory<T> {
    T create(String input);
}
