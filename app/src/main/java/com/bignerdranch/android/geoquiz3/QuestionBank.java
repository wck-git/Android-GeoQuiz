package com.bignerdranch.android.geoquiz3;

public class QuestionBank {
    private final char A = 'A';
    private final char B = 'B';
    private final char C = 'C';
    private final char D = 'D';
    protected static final String EASY = "Easy";
    protected static final String HARD = "Hard";

    private final Question[] mQuestions = new Question[] {
            new Question(R.string.easy_question_oceans, A, EASY),
            new Question(R.string.easy_question_australia, A, EASY),
            new Question(R.string.easy_question_mideast, B, EASY),
            new Question(R.string.easy_question_africa, B, EASY),
            new Question(R.string.easy_question_americas, A, EASY),
            new Question(R.string.easy_question_asia, A, EASY),
            new Question(R.string.easy_question_caspian_sea_largest_lake, A, EASY),
            new Question(R.string.easy_question_mississippi_river_longest_river, B, EASY),
            new Question(R.string.hard_question_world_largest_population, D, HARD, new int[] {R.string.hard_selectionA_world_largest_population, R.string.hard_selectionB_world_largest_population, R.string.hard_selectionC_world_largest_population, R.string.hard_selectionD_world_largest_population}),
            new Question(R.string.hard_question_mexico, C, HARD, new int[] {R.string.hard_selectionA_mexico, R.string.hard_selectionB_mexico, R.string.hard_selectionC_mexico, R.string.hard_selectionD_mexico}),
            new Question(R.string.hard_question_canada, B, HARD, new int[] {R.string.hard_selectionA_canada, R.string.hard_selectionB_canada, R.string.hard_selectionC_canada, R.string.hard_selectionD_canada}),
            new Question(R.string.hard_question_germany, A, HARD, new int[] {R.string.hard_selectionA_germany, R.string.hard_selectionB_germany, R.string.hard_selectionC_germany, R.string.hard_selectionD_germany}),
            new Question(R.string.hard_question_coldest_sea, A, HARD, new int[] {R.string.hard_selectionA_coldest_sea, R.string.hard_selectionB_coldest_sea, R.string.hard_selectionC_coldest_sea, R.string.hard_selectionD_coldest_sea}),
            new Question(R.string.hard_question_white_house_location, D, HARD, new int[] {R.string.hard_selectionA_white_house_location, R.string.hard_selectionB_white_house_location, R.string.hard_selectionC_white_house_location, R.string.hard_selectionD_white_house_location}),
            new Question(R.string.hard_question_US_largest_lake, D, HARD, new int[] {R.string.hard_selectionA_US_largest_lake, R.string.hard_selectionB_US_largest_lake, R.string.hard_selectionC_US_largest_lake, R.string.hard_selectionD_US_largest_lake})
    };

    public Question[] getQuestions() {
        return mQuestions;
    }
}
