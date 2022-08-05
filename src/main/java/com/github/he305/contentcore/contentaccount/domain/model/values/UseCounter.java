package com.github.he305.contentcore.contentaccount.domain.model.values;

import com.github.he305.contentcore.contentaccount.domain.model.exceptions.UseCounterBelowZeroException;
import com.github.he305.contentcore.contentaccount.domain.model.exceptions.UseCounterTooHighException;
import lombok.Getter;

import java.util.Objects;

@Getter
public class UseCounter {
    private int counter;

    public UseCounter() {
        counter = 0;
    }

    public UseCounter(int counter) {
        this.counter = counter;
    }

    public void increaseUseCounter() {
        if (counter == Integer.MAX_VALUE) {
            throw new UseCounterTooHighException();
        }
        counter += 1;
    }

    public void decreaseUseCounter() {
        if (counter == 0) {
            throw new UseCounterBelowZeroException();
        }
        counter -= 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UseCounter that = (UseCounter) o;
        return counter == that.counter;
    }

    @Override
    public int hashCode() {
        return Objects.hash(counter);
    }
}
