package com.echiou.equicaloric;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.text.Editable;
import android.text.TextWatcher;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Dictionary;


public class MainActivity extends Activity {
    final int exerciseType = 0;
    final int caloriesPerHun = 1;
    final String[][] exercisesArray = {
            {"cal", "100"}, //Calories
            {"min", "12"}, //Cycling
            {"min", "12"}, //Jogging
            {"min", "10"}, //Jumping Jacks
            {"min", "25"}, //Leg-lifts
            {"min", "25"}, //Planks
            {"reps", "100"}, //Pullups
            {"reps", "350"}, //Pushups
            {"reps", "200"}, //Situps
            {"reps", "225"}, //Squats
            {"min", "15"}, //Stair-Climbing
            {"min", "13"}, //Swimming
            {"min", "20"}, //Walking
    };
    String reps;
    String rep;
    String minutes;
    String minute;
    String doing;
    String burning;

    int topExercise;
    int botExercise;

    TextView topEquals;
    EditText topNum;
    TextView botNum;
    TextView topUnits;
    TextView botUnits;

    Spinner topSpinner;
    Spinner botSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        reps = getResources().getString(R.string.reps);
        rep = getResources().getString(R.string.rep);
        minutes = getResources().getString(R.string.minutes);
        minute = getResources().getString(R.string.minute);
        doing = getResources().getString(R.string.doing);
        burning = getResources().getString(R.string.burning);

        topExercise = 7;
        botExercise = 0;

        topSpinner = (Spinner) findViewById(R.id.topSpinner);
        botSpinner = (Spinner) findViewById(R.id.botSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.exercises_array, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        topSpinner.setAdapter(adapter);
        topSpinner.setSelection(topExercise);
        botSpinner.setAdapter(adapter);

        topSpinner.setOnItemSelectedListener(topExerciseListener);
        botSpinner.setOnItemSelectedListener(botExerciseListener);

        topEquals = (TextView) findViewById(R.id.top_equals);
        topNum = (EditText) findViewById(R.id.topNumField);
        botNum = (TextView) findViewById(R.id.botNumField);
        topNum.addTextChangedListener(topUnitWatcher);

        topUnits = (TextView) findViewById(R.id.topUnits);
        botUnits = (TextView) findViewById(R.id.botUnits);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private TextWatcher topUnitWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void afterTextChanged(Editable s) {
            String[] topSpecs = exercisesArray[topExercise];
            String topExerciseType = topSpecs[exerciseType];
            String topCaloriesPerHun = topSpecs[caloriesPerHun];
            String[] botSpecs = exercisesArray[botExercise];
            String botExerciseType = botSpecs[exerciseType];
            String botCaloriesPerHun = botSpecs[caloriesPerHun];

            String topNumText = topNum.getText().toString();

            if (!topNumText.equals("")) {
                //Plurality for top
                if (topNumText.equals("1")) {
                    if (topExerciseType.equals("reps")) {
                        topUnits.setText(rep);
                    } else if (topExerciseType.equals("min")) {
                        topUnits.setText(minute);
                    }
                } else {
                    if (topExerciseType.equals("reps")) {
                        topUnits.setText(reps);
                    } else if (topExerciseType.equals("min")) {
                        topUnits.setText(minutes);
                    }
                }

                //Actual Calculations
                int topNumInt = Integer.parseInt(topNumText);
                double botNumDub = (double) topNumInt * Integer.parseInt(botCaloriesPerHun) / Integer.parseInt(topCaloriesPerHun);
                String botNumText = String.valueOf((int) (Math.round(botNumDub * 100000d) / 100000d));
                botNum.setText(botNumText);

                //Plurality for bottom
                if (botNumText.equals("1")) {
                    if (botExerciseType.equals("reps")) {
                        botUnits.setText(rep);
                    } else if (topExerciseType.equals("min")) {
                        botUnits.setText(minute);
                    }
                } else {
                    if (botExerciseType.equals("reps")) {
                        botUnits.setText(reps);
                    } else if (botExerciseType.equals("min")) {
                        botUnits.setText(minutes);
                    }
                }
            } else {
                botNum.setText("0");
                if (topExerciseType.equals("reps")) {
                    topUnits.setText(reps);
                } else if (topExerciseType.equals("min")) {
                    topUnits.setText(minutes);
                }
                if (botExerciseType.equals("reps")) {
                    botUnits.setText(reps);
                } else if (botExerciseType.equals("min")) {
                    botUnits.setText(minutes);
                }
            }
        }
    };

    private AdapterView.OnItemSelectedListener topExerciseListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            topExercise = (int)id;

            String[] topSpecs = exercisesArray[topExercise];
            String topExerciseType = topSpecs[exerciseType];
            String topCaloriesPerHun = topSpecs[caloriesPerHun];
            String[] botSpecs = exercisesArray[botExercise];
            String botExerciseType = botSpecs[exerciseType];
            String botCaloriesPerHun = botSpecs[caloriesPerHun];

            String topNumText = topNum.getText().toString();

            if (!topNumText.equals("")) {
                //Plurality for top
                if (topExerciseType.equals("cal")){
                    topUnits.setText("");
                    topEquals.setText(burning);
                }
                else {
                    topEquals.setText(doing);
                }

                if (topNumText.equals("1")) {
                    if (topExerciseType.equals("reps")) {
                        topUnits.setText(rep);
                    } else if (topExerciseType.equals("min")) {
                        topUnits.setText(minute);
                    }
                } else {
                    if (topExerciseType.equals("reps")) {
                        topUnits.setText(reps);
                    } else if (topExerciseType.equals("min")) {
                        topUnits.setText(minutes);
                    }
                }

                //Actual Calculations
                int topNumInt = Integer.parseInt(topNumText);
                double botNumDub = (double) topNumInt * Integer.parseInt(botCaloriesPerHun) / Integer.parseInt(topCaloriesPerHun);
                String botNumText = String.valueOf((int) (Math.round(botNumDub * 100000d) / 100000d));
                botNum.setText(botNumText);

            } else {
                if (topExerciseType.equals("reps")) {
                    topUnits.setText(reps);
                } else if (topExerciseType.equals("min")) {
                    topUnits.setText(minutes);
                }
                if (botExerciseType.equals("reps")) {
                    botUnits.setText(reps);
                } else if (botExerciseType.equals("min")) {
                    botUnits.setText(minutes);
                }
            }
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    private AdapterView.OnItemSelectedListener botExerciseListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            botExercise = (int)id;

            String[] topSpecs = exercisesArray[topExercise];
            String topExerciseType = topSpecs[exerciseType];
            String topCaloriesPerHun = topSpecs[caloriesPerHun];
            String[] botSpecs = exercisesArray[botExercise];
            String botExerciseType = botSpecs[exerciseType];
            String botCaloriesPerHun = botSpecs[caloriesPerHun];

            String topNumText = topNum.getText().toString();

            if (!topNumText.equals("")) {
                //Actual Calculations
                int topNumInt = Integer.parseInt(topNumText);
                double botNumDub = (double) topNumInt * Integer.parseInt(botCaloriesPerHun) / Integer.parseInt(topCaloriesPerHun);
                String botNumText = String.valueOf((int) (Math.round(botNumDub * 100000d) / 100000d));
                botNum.setText(botNumText);

                //Plurality for bottom
                if (botExerciseType.equals("cal")){
                    botUnits.setText("");
                }
                else if (botNumText.equals("1")) {
                    if (botExerciseType.equals("reps")) {
                        botUnits.setText(rep);
                    } else if (topExerciseType.equals("min")) {
                        botUnits.setText(minute);
                    }
                } else {
                    if (botExerciseType.equals("reps")) {
                        botUnits.setText(reps);
                    } else if (botExerciseType.equals("min")) {
                        botUnits.setText(minutes);
                    }
                }
            } else {
                if (topExerciseType.equals("reps")) {
                    topUnits.setText(reps);
                } else if (topExerciseType.equals("min")) {
                    topUnits.setText(minutes);
                }
                if (botExerciseType.equals("reps")) {
                    botUnits.setText(reps);
                } else if (botExerciseType.equals("min")) {
                    botUnits.setText(minutes);
                }
            }
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };
}
