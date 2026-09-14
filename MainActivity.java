package com.example.algebraventura;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends Activity {

    private static final int PURPLE = Color.rgb(82, 45, 154);
    private static final int PURPLE_DARK = Color.rgb(55, 32, 107);
    private static final int PURPLE_LIGHT = Color.rgb(239, 232, 255);
    private static final int GREEN = Color.rgb(37, 138, 92);
    private static final int GREEN_LIGHT = Color.rgb(226, 246, 235);
    private static final int RED = Color.rgb(190, 55, 55);
    private static final int RED_LIGHT = Color.rgb(255, 232, 232);
    private static final int TEXT = Color.rgb(35, 31, 42);
    private static final int MUTED = Color.rgb(101, 95, 112);
    private static final int WHITE = Color.WHITE;

    private final Random random = new Random();

    private ChallengeSet[] challengeSets;
    private ActiveChallenge[] activeChallenges;
    private int[] lastExerciseIndex;

    private int current = 0;
    private int score = 0;
    private int lives = 3;
    private boolean answered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        challengeSets = buildChallengeSets();
        lastExerciseIndex = new int[challengeSets.length];
        for (int i = 0; i < lastExerciseIndex.length; i++) {
            lastExerciseIndex[i] = -1;
        }
        showHome();
    }

    private ChallengeSet[] buildChallengeSets() {
        return new ChallengeSet[] {
                new ChallengeSet("Mundo 1 · La Plaza del Cuadrado", "Reto 1", buildReto1()),
                new ChallengeSet("Mundo 1 · La Plaza del Cuadrado", "Reto 2", buildReto2()),
                new ChallengeSet("Mundo 1 · La Plaza del Cuadrado", "Reto 3", buildReto3()),
                new ChallengeSet("Mundo 2 · El Valle de la Diferencia", "Reto 4", buildReto4()),
                new ChallengeSet("Mundo 2 · El Valle de la Diferencia", "Reto 5", buildReto5()),
                new ChallengeSet("Mundo 2 · El Valle de la Diferencia", "Reto 6", buildReto6()),
                new ChallengeSet("Mundo 3 · El Puente de los Conjugados", "Reto 7", buildReto7()),
                new ChallengeSet("Mundo 3 · El Puente de los Conjugados", "Reto 8", buildReto8()),
                new ChallengeSet("Mundo 3 · El Puente de los Conjugados", "Reto 9", buildReto9()),
                new ChallengeSet("Mundo 4 · La Fortaleza de los Binomios", "Reto 10", buildReto10()),
                new ChallengeSet("Mundo 4 · La Fortaleza de los Binomios", "Reto 11", buildReto11()),
                new ChallengeSet("Mundo 4 · La Fortaleza de los Binomios", "Reto 12", buildReto12()),
                new ChallengeSet("Mundo 5 · La Cámara del Cubo", "Reto 13 · Cubo de un binomio", buildReto13())
        };
    }

    private Exercise[] buildReto1() {
        Exercise[] bank = new Exercise[20];
        for (int i = 0; i < 20; i++) {
            int a = i + 2;
            int middle = 2 * a;
            int last = a * a;
            bank[i] = new Exercise(
                    "Desarrolla (x + " + a + ")².",
                    new String[] {
                            "x² + " + middle + "x + " + last,
                            "x² + " + last,
                            "x² + " + a + "x + " + last,
                            "x² + " + middle + "x"
                    },
                    0,
                    "Usa (a + b)² = a² + 2ab + b². Entonces (x + " + a + ")² = x² + " + middle + "x + " + last + "."
            );
        }
        return bank;
    }

    private Exercise[] buildReto2() {
        Exercise[] bank = new Exercise[20];
        for (int i = 0; i < 20; i++) {
            int a = i + 2;
            int middle = 2 * a;
            int last = a * a;
            bank[i] = new Exercise(
                    "¿Cuál es el área de un cuadrado cuyo lado mide (m + " + a + ")?",
                    new String[] {
                            "m² + " + middle + "m + " + last,
                            "m² + " + a,
                            "m² + " + last,
                            "2m + " + middle
                    },
                    0,
                    "El área de un cuadrado es lado × lado: (m + " + a + ")² = m² + " + middle + "m + " + last + "."
            );
        }
        return bank;
    }

    private Exercise[] buildReto3() {
        Exercise[] bank = new Exercise[20];
        int index = 0;
        for (int k = 2; k <= 5; k++) {
            for (int b = 2; b <= 6; b++) {
                int central = 2 * k * b;
                int first = k * k;
                int last = b * b;
                bank[index++] = new Exercise(
                        "Completa: (" + k + "a + " + b + ")² = " + first + "a² + ___ + " + last + ".",
                        new String[] {
                                central + "a",
                                (central - 1) + "a",
                                (central + 1) + "a",
                                (k * b) + "a"
                        },
                        0,
                        "El término central de (A + B)² es 2AB. Aquí: 2(" + k + "a)(" + b + ") = " + central + "a."
                );
            }
        }
        return bank;
    }

    private Exercise[] buildReto4() {
        Exercise[] bank = new Exercise[20];
        for (int i = 0; i < 20; i++) {
            int a = i + 2;
            int middle = 2 * a;
            int last = a * a;
            bank[i] = new Exercise(
                    "Desarrolla (x - " + a + ")².",
                    new String[] {
                            "x² - " + middle + "x + " + last,
                            "x² - " + last,
                            "x² + " + middle + "x + " + last,
                            "x² - " + a + "x + " + last
                    },
                    0,
                    "Usa (a - b)² = a² - 2ab + b². Entonces (x - " + a + ")² = x² - " + middle + "x + " + last + "."
            );
        }
        return bank;
    }

    private Exercise[] buildReto5() {
        Exercise[] bank = new Exercise[20];
        int index = 0;
        for (int k = 2; k <= 5; k++) {
            for (int b = 2; b <= 6; b++) {
                int first = k * k;
                int central = 2 * k * b;
                int last = b * b;
                bank[index++] = new Exercise(
                        "¿Cuál expresión equivale a (" + k + "y - " + b + ")²?",
                        new String[] {
                                first + "y² - " + central + "y + " + last,
                                first + "y² - " + last,
                                first + "y² - " + (k * b) + "y + " + last,
                                first + "y² + " + central + "y + " + last
                        },
                        0,
                        "(" + k + "y)² - 2(" + k + "y)(" + b + ") + " + b + "² = " + first + "y² - " + central + "y + " + last + "."
                );
            }
        }
        return bank;
    }

    private Exercise[] buildReto6() {
        Exercise[] bank = new Exercise[20];
        for (int i = 0; i < 20; i++) {
            int a = i + 2;
            int middle = 2 * a;
            int k = a * a;
            bank[i] = new Exercise(
                    "Si (p - " + a + ")² = p² - " + middle + "p + k, ¿cuánto vale k?",
                    new String[] {
                            String.valueOf(k),
                            String.valueOf(k + 1),
                            String.valueOf(k + a),
                            String.valueOf(k + middle)
                    },
                    0,
                    "En (p - " + a + ")², el último término es " + a + "² = " + k + "."
            );
        }
        return bank;
    }

    private Exercise[] buildReto7() {
        Exercise[] bank = new Exercise[20];
        for (int i = 0; i < 20; i++) {
            int a = i + 2;
            int square = a * a;
            int middle = 2 * a;
            bank[i] = new Exercise(
                    "Resuelve (x + " + a + ")(x - " + a + ").",
                    new String[] {
                            "x² - " + square,
                            "x² + " + square,
                            "x² - " + middle + "x + " + square,
                            "x² + " + middle + "x - " + square
                    },
                    0,
                    "La suma por diferencia cumple (A + B)(A - B) = A² - B². Por eso: x² - " + square + "."
            );
        }
        return bank;
    }

    private Exercise[] buildReto8() {
        Exercise[] bank = new Exercise[20];
        int index = 0;
        for (int k = 2; k <= 5; k++) {
            for (int b = 2; b <= 6; b++) {
                int first = k * k;
                int last = b * b;
                int middle = 2 * k * b;
                bank[index++] = new Exercise(
                        "Simplifica (" + k + "m + " + b + ")(" + k + "m - " + b + ").",
                        new String[] {
                                first + "m² - " + last,
                                first + "m² + " + last,
                                k + "m² - " + last,
                                first + "m² - " + middle + "m + " + last
                        },
                        0,
                        "Es una suma por diferencia: (" + k + "m)² - " + b + "² = " + first + "m² - " + last + "."
                );
            }
        }
        return bank;
    }

    private Exercise[] buildReto9() {
        Exercise[] bank = new Exercise[20];
        int index = 0;
        for (int k = 2; k <= 5; k++) {
            for (int b = 2; b <= 6; b++) {
                int first = k * k;
                int last = b * b;
                String left = k + "x";
                bank[index++] = new Exercise(
                        "¿Qué producto notable produce " + first + "x² - " + last + "?",
                        new String[] {
                                "(" + left + " + " + b + ")(" + left + " - " + b + ")",
                                "(" + left + " + " + b + ")²",
                                "(" + left + " - " + b + ")²",
                                "(" + left + " + " + b + ")(" + left + " + " + b + ")"
                        },
                        0,
                        first + "x² - " + last + " = (" + left + ")² - " + b + "², una diferencia de cuadrados."
                );
            }
        }
        return bank;
    }

    private Exercise[] buildReto10() {
        Exercise[] bank = new Exercise[20];
        int index = 0;
        for (int a = 2; a <= 5; a++) {
            for (int b = 6; b <= 10; b++) {
                int sum = a + b;
                int product = a * b;
                bank[index++] = new Exercise(
                        "Desarrolla (x + " + a + ")(x + " + b + ").",
                        new String[] {
                                "x² + " + sum + "x + " + product,
                                "x² + " + product + "x + " + sum,
                                "x² + " + (b - a) + "x + " + product,
                                "x² + " + sum + "x + " + sum
                        },
                        0,
                        "Multiplica término a término: x² + " + b + "x + " + a + "x + " + product + " = x² + " + sum + "x + " + product + "."
                );
            }
        }
        return bank;
    }

    private Exercise[] buildReto11() {
        Exercise[] bank = new Exercise[20];
        int index = 0;
        for (int a = 2; a <= 5; a++) {
            for (int b = 6; b <= 10; b++) {
                int middle = b - a;
                int product = a * b;
                bank[index++] = new Exercise(
                        "¿Cuál es el resultado de (x - " + a + ")(x + " + b + ")?",
                        new String[] {
                                "x² + " + middle + "x - " + product,
                                "x² - " + middle + "x - " + product,
                                "x² + " + (a + b) + "x + " + product,
                                "x² + " + middle + "x + " + product
                        },
                        0,
                        "x² + " + b + "x - " + a + "x - " + product + " = x² + " + middle + "x - " + product + "."
                );
            }
        }
        return bank;
    }

    private Exercise[] buildReto12() {
        Exercise[] bank = new Exercise[20];
        for (int i = 0; i < 20; i++) {
            int a = i + 2;
            int middle = 2 * a;
            int square = a * a;
            bank[i] = new Exercise(
                    "Un cuadrado grande tiene lado (x + " + a + ") y se retira un cuadrado pequeño de lado " + a + ". ¿Qué expresión representa el área restante?",
                    new String[] {
                            "x² + " + middle + "x",
                            "x² + " + square,
                            "x² + " + middle + "x + " + square,
                            "x² + " + a + "x"
                    },
                    0,
                    "Área restante = (x + " + a + ")² - " + a + "² = x² + " + middle + "x + " + square + " - " + square + " = x² + " + middle + "x."
            );
        }
        return bank;
    }

    private Exercise[] buildReto13() {
        Exercise[] bank = new Exercise[20];
        for (int i = 0; i < 20; i++) {
            int a = i + 1;
            boolean plus = i % 2 == 0;
            int c1 = 3 * a;
            int c2 = 3 * a * a;
            int c3 = a * a * a;

            String sign = plus ? "+" : "-";
            String prompt = "Desarrolla (x " + sign + " " + a + ")³.";
            String correct;
            String wrongLast;
            String simple;
            String weakCoefficients;
            String explanation;

            if (plus) {
                correct = "x³ + " + c1 + "x² + " + c2 + "x + " + c3;
                wrongLast = "x³ + " + c1 + "x² + " + c2 + "x - " + c3;
                simple = "x³ + " + c3;
                weakCoefficients = "x³ + " + a + "x² + " + (a * a) + "x + " + c3;
                explanation = "Usa (A + B)³ = A³ + 3A²B + 3AB² + B³. Entonces (x + " + a + ")³ = " + correct + ".";
            } else {
                correct = "x³ - " + c1 + "x² + " + c2 + "x - " + c3;
                wrongLast = "x³ - " + c1 + "x² + " + c2 + "x + " + c3;
                simple = "x³ - " + c3;
                weakCoefficients = "x³ - " + a + "x² + " + (a * a) + "x - " + c3;
                explanation = "Usa (A - B)³ = A³ - 3A²B + 3AB² - B³. Entonces (x - " + a + ")³ = " + correct + ".";
            }

            bank[i] = new Exercise(
                    prompt,
                    new String[] {correct, wrongLast, simple, weakCoefficients},
                    0,
                    explanation
            );
        }
        return bank;
    }

    private void showHome() {
        ScrollView scroll = baseScroll();
        LinearLayout root = contentColumn();
        scroll.addView(root);

        TextView badge = label("MATEMÁTICAS · GRADO 8° · VERSIÓN 2.0", 13, PURPLE, true);
        badge.setGravity(Gravity.CENTER);
        badge.setBackground(rounded(PURPLE_LIGHT, 999));
        LinearLayout.LayoutParams badgeParams = wrap();
        badgeParams.gravity = Gravity.CENTER_HORIZONTAL;
        badgeParams.bottomMargin = dp(18);
        badge.setLayoutParams(badgeParams);
        badge.setPadding(dp(14), dp(7), dp(14), dp(7));
        root.addView(badge);

        TextView title = label("AlgebrAventura 2.0", 34, PURPLE_DARK, true);
        title.setGravity(Gravity.CENTER);
        root.addView(title, matchWrap());

        TextView subtitle = label("El Reino de los Productos Notables", 20, TEXT, true);
        subtitle.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams subParams = matchWrap();
        subParams.topMargin = dp(6);
        subParams.bottomMargin = dp(24);
        root.addView(subtitle, subParams);

        LinearLayout mission = card();
        mission.addView(label("Tu misión", 20, PURPLE_DARK, true));
        TextView missionText = label(
                "Supera 5 mundos algebraicos, conserva tus 3 vidas y resuelve una aventura diferente en cada partida.",
                16, TEXT, false);
        LinearLayout.LayoutParams mtp = matchWrap();
        mtp.topMargin = dp(10);
        mission.addView(missionText, mtp);
        TextView facts = label("13 retos · 20 ejercicios por reto · 260 ejercicios en el banco · 100 puntos por acierto", 14, MUTED, false);
        LinearLayout.LayoutParams fp = matchWrap();
        fp.topMargin = dp(12);
        mission.addView(facts, fp);
        TextView randomInfo = label("En cada partida se selecciona aleatoriamente 1 ejercicio de cada banco y también se mezclan las respuestas.", 14, MUTED, false);
        LinearLayout.LayoutParams rip = matchWrap();
        rip.topMargin = dp(8);
        mission.addView(randomInfo, rip);
        root.addView(mission, cardParams());

        addWorld(root, "MUNDO 1", "La Plaza del Cuadrado", "(a + b)²");
        addWorld(root, "MUNDO 2", "El Valle de la Diferencia", "(a - b)²");
        addWorld(root, "MUNDO 3", "El Puente de los Conjugados", "(a + b)(a - b)");
        addWorld(root, "MUNDO 4", "La Fortaleza de los Binomios", "(x + a)(x + b)");
        addWorld(root, "MUNDO 5", "La Cámara del Cubo", "(a ± b)³");

        Button start = primaryButton("COMENZAR AVENTURA");
        start.setOnClickListener(v -> restartGame());
        LinearLayout.LayoutParams sp = matchWrap();
        sp.topMargin = dp(14);
        sp.bottomMargin = dp(22);
        root.addView(start, sp);

        setContentView(scroll);
    }

    private void restartGame() {
        current = 0;
        score = 0;
        lives = 3;
        answered = false;
        chooseRandomExercises();
        showGame();
    }

    private void chooseRandomExercises() {
        activeChallenges = new ActiveChallenge[challengeSets.length];

        for (int i = 0; i < challengeSets.length; i++) {
            ChallengeSet set = challengeSets[i];
            int selected;
            do {
                selected = random.nextInt(set.exercises.length);
            } while (set.exercises.length > 1 && selected == lastExerciseIndex[i]);

            lastExerciseIndex[i] = selected;
            Exercise randomized = shuffledCopy(set.exercises[selected]);
            activeChallenges[i] = new ActiveChallenge(set.world, set.title, randomized);
        }
    }

    private Exercise shuffledCopy(Exercise source) {
        List<Integer> order = new ArrayList<>();
        for (int i = 0; i < source.options.length; i++) {
            order.add(i);
        }
        Collections.shuffle(order, random);

        String[] shuffled = new String[source.options.length];
        int newCorrectIndex = 0;

        for (int i = 0; i < order.size(); i++) {
            int oldIndex = order.get(i);
            shuffled[i] = source.options[oldIndex];
            if (oldIndex == source.correctIndex) {
                newCorrectIndex = i;
            }
        }

        return new Exercise(source.prompt, shuffled, newCorrectIndex, source.explanation);
    }

    private void showGame() {
        final ActiveChallenge active = activeChallenges[current];
        final Exercise challenge = active.exercise;

        ScrollView scroll = baseScroll();
        LinearLayout root = contentColumn();
        scroll.addView(root);

        LinearLayout top = new LinearLayout(this);
        top.setOrientation(LinearLayout.HORIZONTAL);
        top.setGravity(Gravity.CENTER_VERTICAL);

        TextView progressText = label("Reto " + (current + 1) + " de " + activeChallenges.length, 14, MUTED, true);
        top.addView(progressText, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));

        TextView livesText = label(heartText(), 17, RED, true);
        livesText.setGravity(Gravity.RIGHT);
        top.addView(livesText, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        root.addView(top, matchWrap());

        ProgressBar bar = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
        bar.setMax(activeChallenges.length);
        bar.setProgress(current + 1);
        LinearLayout.LayoutParams bp = matchWrap();
        bp.topMargin = dp(8);
        bp.bottomMargin = dp(18);
        bp.height = dp(8);
        root.addView(bar, bp);

        TextView world = label(active.world, 14, PURPLE, true);
        root.addView(world, matchWrap());

        TextView heading = label(active.title, 27, PURPLE_DARK, true);
        LinearLayout.LayoutParams hp = matchWrap();
        hp.topMargin = dp(5);
        root.addView(heading, hp);

        LinearLayout questionCard = card();
        TextView prompt = label(challenge.prompt, 21, TEXT, true);
        prompt.setGravity(Gravity.CENTER);
        prompt.setPadding(0, dp(8), 0, dp(8));
        questionCard.addView(prompt, matchWrap());
        LinearLayout.LayoutParams qcp = cardParams();
        qcp.topMargin = dp(16);
        root.addView(questionCard, qcp);

        TextView instruction = label("Selecciona la respuesta correcta:", 15, MUTED, true);
        LinearLayout.LayoutParams ip = matchWrap();
        ip.topMargin = dp(6);
        ip.bottomMargin = dp(8);
        root.addView(instruction, ip);

        final Button[] optionButtons = new Button[challenge.options.length];
        for (int i = 0; i < challenge.options.length; i++) {
            final int selectedIndex = i;
            Button option = optionButton(letter(i) + ".  " + challenge.options[i]);
            option.setOnClickListener(v -> {
                if (!answered) {
                    answered = true;
                    if (selectedIndex == challenge.correctIndex) {
                        score += 100;
                    } else {
                        lives = Math.max(0, lives - 1);
                    }
                    renderAnswered(root, challenge, optionButtons, selectedIndex);
                }
            });
            optionButtons[i] = option;
            LinearLayout.LayoutParams op = matchWrap();
            op.bottomMargin = dp(10);
            root.addView(option, op);
        }

        TextView scoreText = label("Puntaje: " + score, 14, MUTED, true);
        scoreText.setTag("scoreText");
        LinearLayout.LayoutParams scp = matchWrap();
        scp.topMargin = dp(4);
        root.addView(scoreText, scp);

        setContentView(scroll);
    }

    private void renderAnswered(LinearLayout root, Exercise challenge, Button[] optionButtons, int selectedIndex) {
        for (int i = 0; i < optionButtons.length; i++) {
            optionButtons[i].setEnabled(false);
            if (i == challenge.correctIndex) {
                styleOption(optionButtons[i], GREEN_LIGHT, GREEN);
            } else if (i == selectedIndex) {
                styleOption(optionButtons[i], RED_LIGHT, RED);
            } else {
                styleOption(optionButtons[i], Color.rgb(247, 245, 250), Color.rgb(205, 199, 214));
            }
        }

        TextView existingScore = (TextView) root.findViewWithTag("scoreText");
        if (existingScore != null) {
            existingScore.setText("Puntaje: " + score + "    ·    Vidas: " + lives);
        }

        LinearLayout feedback = card();
        boolean correct = selectedIndex == challenge.correctIndex;
        feedback.setBackground(rounded(correct ? GREEN_LIGHT : RED_LIGHT, 18));

        TextView result = label(correct ? "¡Correcto! +100 puntos" : "Aún no. Revisa la explicación.", 18,
                correct ? GREEN : RED, true);
        feedback.addView(result, matchWrap());

        TextView explanation = label(challenge.explanation, 15, TEXT, false);
        LinearLayout.LayoutParams ep = matchWrap();
        ep.topMargin = dp(8);
        feedback.addView(explanation, ep);

        LinearLayout.LayoutParams fbp = cardParams();
        fbp.topMargin = dp(14);
        root.addView(feedback, fbp);

        Button next = primaryButton((current == activeChallenges.length - 1 || lives == 0) ? "VER RESULTADO" : "SIGUIENTE RETO");
        next.setOnClickListener(v -> {
            if (current == activeChallenges.length - 1 || lives == 0) {
                showResult();
            } else {
                current++;
                answered = false;
                showGame();
            }
        });
        LinearLayout.LayoutParams np = matchWrap();
        np.topMargin = dp(4);
        np.bottomMargin = dp(20);
        root.addView(next, np);
    }

    private void showResult() {
        ScrollView scroll = baseScroll();
        LinearLayout root = contentColumn();
        root.setGravity(Gravity.CENTER_HORIZONTAL);
        scroll.addView(root);

        TextView title = label(lives > 0 ? "¡Misión completada!" : "Fin de la expedición", 30, PURPLE_DARK, true);
        title.setGravity(Gravity.CENTER);
        root.addView(title, matchWrap());

        TextView trophy = label(score >= 1200 ? "★ ★ ★" : score >= 900 ? "★ ★" : "★", 38, PURPLE, true);
        trophy.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams tp = matchWrap();
        tp.topMargin = dp(10);
        root.addView(trophy, tp);

        LinearLayout resultCard = card();
        TextView scoreBig = label(score + " / " + (activeChallenges.length * 100), 34, PURPLE_DARK, true);
        scoreBig.setGravity(Gravity.CENTER);
        resultCard.addView(scoreBig, matchWrap());

        TextView scoreLabel = label("PUNTOS", 13, MUTED, true);
        scoreLabel.setGravity(Gravity.CENTER);
        resultCard.addView(scoreLabel, matchWrap());

        TextView message = label(resultMessage(), 17, TEXT, false);
        message.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams mp = matchWrap();
        mp.topMargin = dp(16);
        resultCard.addView(message, mp);

        TextView livesLeft = label("Vidas restantes: " + lives, 15, MUTED, true);
        livesLeft.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams lp = matchWrap();
        lp.topMargin = dp(12);
        resultCard.addView(livesLeft, lp);
        root.addView(resultCard, cardParams());

        Button replay = primaryButton("JUGAR DE NUEVO CON NUEVOS EJERCICIOS");
        replay.setOnClickListener(v -> restartGame());
        LinearLayout.LayoutParams rp = matchWrap();
        rp.topMargin = dp(8);
        root.addView(replay, rp);

        Button home = secondaryButton("VOLVER AL INICIO");
        home.setOnClickListener(v -> showHome());
        LinearLayout.LayoutParams hop = matchWrap();
        hop.topMargin = dp(10);
        hop.bottomMargin = dp(24);
        root.addView(home, hop);

        setContentView(scroll);
    }

    private String resultMessage() {
        if (score >= 1200) return "Dominio sobresaliente. Reconoces y aplicas productos notables, incluido el cubo de un binomio, con mucha precisión.";
        if (score >= 900) return "Muy buen trabajo. Tienes una base sólida; revisa los retos en los que dudaste.";
        if (score >= 600) return "Vas por buen camino. Repite la aventura: cada partida trae ejercicios diferentes.";
        return "Necesitas reforzar las estructuras básicas. Usa las explicaciones y vuelve a intentarlo con una nueva selección de ejercicios.";
    }

    private void addWorld(LinearLayout parent, String number, String name, String formula) {
        LinearLayout world = card();

        TextView n = label(number, 12, PURPLE, true);
        world.addView(n, matchWrap());

        TextView nameView = label(name, 17, TEXT, true);
        LinearLayout.LayoutParams np = matchWrap();
        np.topMargin = dp(3);
        world.addView(nameView, np);

        TextView formulaView = label(formula, 18, PURPLE_DARK, true);
        LinearLayout.LayoutParams fp = matchWrap();
        fp.topMargin = dp(7);
        world.addView(formulaView, fp);

        LinearLayout.LayoutParams wp = cardParams();
        wp.topMargin = dp(9);
        wp.bottomMargin = dp(0);
        parent.addView(world, wp);
    }

    private ScrollView baseScroll() {
        ScrollView scroll = new ScrollView(this);
        scroll.setFillViewport(true);
        scroll.setBackgroundColor(Color.rgb(250, 248, 253));
        return scroll;
    }

    private LinearLayout contentColumn() {
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(dp(20), dp(26), dp(20), dp(20));
        root.setGravity(Gravity.TOP);
        return root;
    }

    private LinearLayout card() {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(dp(18), dp(18), dp(18), dp(18));
        card.setBackground(rounded(WHITE, 18));
        card.setElevation(dp(2));
        return card;
    }

    private TextView label(String text, int sizeSp, int color, boolean bold) {
        TextView view = new TextView(this);
        view.setText(text);
        view.setTextSize(sizeSp);
        view.setTextColor(color);
        view.setLineSpacing(0, 1.12f);
        if (bold) view.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        return view;
    }

    private Button primaryButton(String text) {
        Button button = new Button(this);
        button.setText(text);
        button.setTextColor(WHITE);
        button.setTextSize(15);
        button.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        button.setAllCaps(false);
        button.setPadding(dp(14), dp(12), dp(14), dp(12));
        button.setBackground(rounded(PURPLE, 16));
        return button;
    }

    private Button secondaryButton(String text) {
        Button button = primaryButton(text);
        button.setTextColor(PURPLE_DARK);
        button.setBackground(rounded(PURPLE_LIGHT, 16));
        return button;
    }

    private Button optionButton(String text) {
        Button button = new Button(this);
        button.setText(text);
        button.setTextColor(TEXT);
        button.setTextSize(16);
        button.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        button.setAllCaps(false);
        button.setPadding(dp(15), dp(12), dp(15), dp(12));
        styleOption(button, WHITE, Color.rgb(220, 214, 228));
        return button;
    }

    private void styleOption(Button button, int fill, int stroke) {
        GradientDrawable bg = rounded(fill, 14);
        bg.setStroke(dp(1), stroke);
        button.setBackground(bg);
    }

    private GradientDrawable rounded(int color, int radiusDp) {
        GradientDrawable bg = new GradientDrawable();
        bg.setColor(color);
        bg.setCornerRadius(dp(radiusDp));
        return bg;
    }

    private LinearLayout.LayoutParams matchWrap() {
        return new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    private LinearLayout.LayoutParams wrap() {
        return new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    private LinearLayout.LayoutParams cardParams() {
        LinearLayout.LayoutParams params = matchWrap();
        params.topMargin = dp(12);
        params.bottomMargin = dp(8);
        return params;
    }

    private int dp(int value) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(value * density);
    }

    private String heartText() {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            if (i < lives) b.append("♥"); else b.append("♡");
            if (i < 2) b.append(" ");
        }
        return b.toString();
    }

    private String letter(int i) {
        return String.valueOf((char) ('A' + i));
    }

    private static class ChallengeSet {
        final String world;
        final String title;
        final Exercise[] exercises;

        ChallengeSet(String world, String title, Exercise[] exercises) {
            this.world = world;
            this.title = title;
            this.exercises = exercises;
        }
    }

    private static class ActiveChallenge {
        final String world;
        final String title;
        final Exercise exercise;

        ActiveChallenge(String world, String title, Exercise exercise) {
            this.world = world;
            this.title = title;
            this.exercise = exercise;
        }
    }

    private static class Exercise {
        final String prompt;
        final String[] options;
        final int correctIndex;
        final String explanation;

        Exercise(String prompt, String[] options, int correctIndex, String explanation) {
            this.prompt = prompt;
            this.options = options;
            this.correctIndex = correctIndex;
            this.explanation = explanation;
        }
    }
}
