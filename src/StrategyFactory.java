public class StrategyFactory {
    public static Strategy getStrategy(String strategy) {
        if (strategy.equals("BestPartialScore"))
            return new BestPartialScore();

        if (strategy.equals("BestExamScore"))
            return new BestExamScore();

        if (strategy.equals("BestTotalScore"))
            return new BestTotalScore();

        return null;
    }
}
