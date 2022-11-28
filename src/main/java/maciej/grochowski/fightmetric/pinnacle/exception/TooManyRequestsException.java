package maciej.grochowski.fightmetric.pinnacle.exception;

public class TooManyRequestsException extends RuntimeException{

    public static final String errorMessage = "Failed to fetch data from pinnacle api - the limit of monthly requests has been exceeded.";

    public TooManyRequestsException() {
        super(errorMessage);
    }
}
