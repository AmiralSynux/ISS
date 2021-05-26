package service;

public interface IObservable {
    void attach(IObserver observer);
    void detach(IObserver observer);
    void notifyObs();
}
