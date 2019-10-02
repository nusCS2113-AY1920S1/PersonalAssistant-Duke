package controllers;

public interface IArchDukeFactory<T> {
    T create(String input);
}
