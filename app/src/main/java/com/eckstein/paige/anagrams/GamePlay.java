package com.eckstein.paige.anagrams;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class GamePlay {

    //holds application context to access resources
    private Context context;
    //holds all possible valid English words
    private Set<String> dictionary;
    //hold all possible letter tiles with Scrabble style distribution
    private int[] tiles;
    //used to randomly select tile for board from tiles array
    private Random rng;
    //holds individual letter scores
    private HashMap<Character, Integer> score;
    //holds words and their scores made by the user during game play
    private HashMap<String, Integer> wordScores;

    /**
     * Constructor
     *
     * @param context context passed by application, used to gain access
     *                to resources
     *
     * Populates tiles array with Scrabble inspired distribution of letters:
     *      a:.09   b:.02   c:.02   d:.04   e:.12   f:.02   g:.03
     *      h:.02   i:.09   j:.02   k:.02   l:.04   m:.04   n:.04
     *      o:.08   p:.02   q:.01   r:.06   s:.04   t:.06   u:.04
     *      v:.02   w:.02   x:.01   y:.02   z:.01
     *
     * Initializes score HashMap <String, Integer> that contains individual letter
     * scores in the form <"a":1> and populates it with hardcoded scores.
     * The hardcoded scores can be modified in the setupScores module.
     *
     * Initializes empty HasHMap <String, Integer> that will be used to hold
     * words submitted by the user during game play. Format will be <"Word":55>
     * This will be used by the the GameOver activity to display words found
     * during the game, as well as their accompanying score.
     *
     * Initialize Random Number Generator
     */
    public GamePlay(Context context)
    {
        this.context = context;

        tiles = new int[]{R.drawable.a, R.drawable.a, R.drawable.a, R.drawable.a, R.drawable.a,
                          R.drawable.a, R.drawable.a, R.drawable.a, R.drawable.a, R.drawable.b,
                          R.drawable.b, R.drawable.c, R.drawable.c, R.drawable.d, R.drawable.d,
                          R.drawable.d, R.drawable.d, R.drawable.e, R.drawable.e, R.drawable.e,
                          R.drawable.e, R.drawable.e, R.drawable.e, R.drawable.e, R.drawable.e,
                          R.drawable.e, R.drawable.e, R.drawable.e, R.drawable.e, R.drawable.f,
                          R.drawable.f, R.drawable.g, R.drawable.g, R.drawable.g, R.drawable.h,
                          R.drawable.h, R.drawable.i, R.drawable.i, R.drawable.i, R.drawable.i,
                          R.drawable.i, R.drawable.i, R.drawable.i, R.drawable.i, R.drawable.i,
                          R.drawable.j, R.drawable.j, R.drawable.k, R.drawable.k, R.drawable.l,
                          R.drawable.l, R.drawable.l, R.drawable.l, R.drawable.m, R.drawable.m,
                          R.drawable.m, R.drawable.m, R.drawable.n, R.drawable.n, R.drawable.n,
                          R.drawable.n, R.drawable.o, R.drawable.o, R.drawable.o, R.drawable.o,
                          R.drawable.o, R.drawable.o, R.drawable.o, R.drawable.o, R.drawable.p,
                          R.drawable.p, R.drawable.q_pink_text, R.drawable.r, R.drawable.r, R.drawable.r,
                          R.drawable.r, R.drawable.r, R.drawable.r, R.drawable.s, R.drawable.s,
                          R.drawable.s, R.drawable.s, R.drawable.t, R.drawable.t, R.drawable.t,
                          R.drawable.t, R.drawable.t, R.drawable.t, R.drawable.u, R.drawable.u,
                          R.drawable.u, R.drawable.u, R.drawable.v, R.drawable.v, R.drawable.w,
                          R.drawable.w, R.drawable.x_pink_text, R.drawable.y, R.drawable.y, R.drawable.z_pink_text};

        score = new HashMap<>();
        wordScores = new HashMap<>();

        setupScore();
        populateDictionary();
        rng = new Random();
    }

    /**
     * Populates dictionary with all valid alpha English words, pulled from list provided by:
     *      dwyl: https://github.com/dwyl/english-words
     * This locates the file in app, reads in words one by one,
     * then creates Set of words
     */
    private void populateDictionary()
    {
        dictionary = new HashSet<>();
        BufferedReader br = null;
        try
        {
            InputStream is = context.getResources().openRawResource(R.raw.words);
            br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while((line = br.readLine())!= null)
            {
                dictionary.add(line);
            }
        } catch (IOException io)
        {
            Log.e("DICTIONARY", "ERROR: "+io);
        } finally
        {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    /**
     * Validates if a given input is a meaningful word in English
     * returns true if valid, false otherwise
     *
     * Checks validity against dictionary of all accepted valid English words
     * found in "words.txt" at root directory. List of words provided by:
     *      dwyl: https://github.com/dwyl/english-words
     *
     * @param input user submitted String to test if valid word
     * @return  true if user submitted String is a valid English word
     *          false otherwise
     */
    public boolean validWord(String input)
    {
        return dictionary.contains(input.toLowerCase());
    }

    /**
     * Called once a user submitted word has been validated
     * Used to tally a score for a given word
     * Determining letter scores are held in the score HasHMap <Character, Integer>
     * @param word  String: validated user submitted word to be scored
     * @return  integer score for submitted word
     */
    public int scoreWord(String word)
    {
        int score = 0;
        word = word.toLowerCase();

        for(int i = 0; i < word.length(); i++)
        {
            score += getLetterScore(word.charAt(i));
        }

        return score;
    }

    /**
     * getLetterScore returns the integer score of an individual
     * letter
     *
     * Example:
     * Input: "a"
     * Output: 1
     *
     * Queries the score HashMap for letter-score mapping
     *
     * @param letter the individual letter to retrieve the score of
     * @return  integer score of individual letter
     */
    public int getLetterScore(char letter)
    {
        return score.get(letter);
    }

    /**
     * addWord is called when a user has submitted a valid word
     * during game play.
     * The word is scored and added to the wordScores HashMap
     * <String, Integer> in the form:
     *      <"word", 10>
     *
     *  This HashMap will be used to display final words and
     *  their resulting scores in the GameOver activity
     *
     * @param word String: validated and scored word submitted
     *             by the user during game play
     * @param score integer: corresponding score of the validated
     *              word submitted by the user (@param word)
     */
    public void addWord(String word, int score)
    {
        wordScores.put(word, score);
    }

    /**
     * getWordScores returns the entire wordScores HashMap
     * <String, Integer>.
     * This HashMap contains all the validated words the
     * user has submitted during a single game play instance
     * along with the word's corresponding score.
     *
     * @return wordScores HashMap<String, Integer> containing
     *          all validated words submitted by the user
     *          during game play along with their corresponding
     *          scores. HashMap of form:
     *          <"word", 10>
     */
    public HashMap<String, Integer> getWordScores() {
        return wordScores;
    }

    /**
     * getRandomTile randomly selects a drawable's id from the tiles
     * array. This uses a scrabble style distribution of letters
     * and can be modified in the default constructor of this class.
     *
     * @return  integer drawable id mapped to a letter tile image
     *           randomly selected from tiles array
     */
    public int getRandomTile()
    {
        return tiles[rng.nextInt(99)];
    }

    /**
     * setupScore populates the score HashMap <Character, Integer>
     * This HashMap holds the score of an individual letter in
     * the form <'a':1>
     */
    private void setupScore()
    {
        score.put('a', 1);
        score.put('b', 3);
        score.put('c', 3);
        score.put('d', 3);
        score.put('e', 1);
        score.put('f', 3);
        score.put('g', 3);
        score.put('h', 3);
        score.put('i', 1);
        score.put('j', 5);
        score.put('k', 5);
        score.put('l', 1);
        score.put('m', 1);
        score.put('n', 1);
        score.put('o', 1);
        score.put('p', 3);
        score.put('q', 10);
        score.put('r', 1);
        score.put('s', 1);
        score.put('t', 1);
        score.put('u', 1);
        score.put('v', 5);
        score.put('w', 5);
        score.put('x', 10);
        score.put('y', 5);
        score.put('z', 10);
    }

    /**
     * getTileLetter returns the String letter of a tile's image
     * @param drawable integer drawable id of letter tile image
     *                 to retrieve String letter from
     * @return String version of what is displayed on tile's image
     */
    public String getTileLetter(int drawable)
    {
       switch(drawable)
       {
           case R.drawable.a:
           {
               return "a";
           }
           case R.drawable.b:
           {
               return "b";
           }
           case R.drawable.c:
           {
               return "c";
           }
           case R.drawable.d:
           {
               return "d";
           }
           case R.drawable.e:
           {
               return "e";
           }
           case R.drawable.f:
           {
               return "f";
           }
           case R.drawable.g:
           {
               return "g";
           }
           case R.drawable.h:
           {
               return "h";
           }
           case R.drawable.i:
           {
               return "i";
           }
           case R.drawable.j:
           {
               return "j";
           }
           case R.drawable.k:
           {
               return "k";
           }
           case R.drawable.l:
           {
               return "l";
           }
           case R.drawable.m:
           {
               return "m";
           }
           case R.drawable.n:
           {
               return "n";
           }
           case R.drawable.o:
           {
               return "o";
           }
           case R.drawable.p:
           {
               return "p";
           }
           case R.drawable.q:
           {
               return "q";
           }
           case R.drawable.r:
           {
               return "r";
           }
           case R.drawable.s:
           {
               return "s";
           }
           case R.drawable.t:
           {
               return "t";
           }
           case R.drawable.u:
           {
               return "u";
           }
           case R.drawable.v:
           {
               return "v";
           }
           case R.drawable.w:
           {
               return "w";
           }
           case R.drawable.x:
           {
               return "x";
           }
           case R.drawable.y:
           {
               return "y";
           }
           case R.drawable.z:
           {
               return "z";
           }
       }

       return "a";
    }
}
