package lk.nirmalcode.cpmadfinal.utils;

import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

public class DialogUtils {

    public final static int MODE_ADD = 0;
    public final static int MODE_UPDATE = 1;
    public final static int MODE_DELETE = 2;
    public final static int MODE_HIDE = 3;
    public final static int MODE_SHOW = 4;
    public final static int MODE_REPORT = 5;

    static DBHelper dbHelper;
    static GlobalClass globalClass;
    static LayoutInflater inflater;
    static View content;
    static AlertDialog.Builder dialog;



//    public static void showQuestionManageDialog(int mode, final AppCompatActivity parentActivity, final Question question) {
//        dbHelper = new DBHelper();
//        globalClass = (GlobalClass) parentActivity.getApplicationContext();
//        inflater = parentActivity.getLayoutInflater();
//        content = inflater.inflate(R.layout.dialog_add_question, null);
//        dialog = new AlertDialog.Builder(new ContextWrapper(parentActivity), R.style.AppTheme_Dialog);
//        dialog.setView(content);
//
//        final EditText txtTitle = content.findViewById(R.id.txtDialogQuestionTitle);
//        final EditText txtDescription = content.findViewById(R.id.txtDialogQuestionDescription);
//
//        final Spinner spnCategory = content.findViewById(R.id.spnDIalogQuestionCategory);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(parentActivity,
//                R.array.question_category_array, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spnCategory.setAdapter(adapter);
//
//        switch (mode) {
//            case MODE_ADD:
//                dialog.setTitle(parentActivity.getString(R.string.dialog_add_new_question_title));
//                dialog.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                        if (spnCategory.getSelectedItemPosition() > 0) {
//                            String title = txtTitle.getText().toString();
//                            String description = txtDescription.getText().toString();
//                            String category = spnCategory.getSelectedItem().toString();
//                            Long nowTime = System.currentTimeMillis();
//
//                            Question question = new Question(title, description, category, nowTime, globalClass.getLoggedInUser().KEY);
//
//                            dbHelper.pushObject(globalClass.REF_QUESTIONS + "/" + category, question);
//                            dialog.dismiss();
//                        } else {
//                            Toast.makeText(parentActivity, parentActivity.getString(R.string.error_question_add_category), Toast.LENGTH_LONG).show();
//                        }
//                    }
//                });
//                break;
//            case MODE_UPDATE:
//                dialog.setTitle(parentActivity.getString(R.string.dialog_update_question_title));
//                txtTitle.setText(question.questionTitle);
//                txtDescription.setText(question.questionDescription);
//                spnCategory.setSelection(adapter.getPosition(question.questionCategory));
//
//                dialog.setPositiveButton(R.string.update, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                        String title = txtTitle.getText().toString();
//                        String description = txtDescription.getText().toString();
//                        String category = spnCategory.getSelectedItem().toString();
//                        Long nowTime = System.currentTimeMillis();
//
//                        Question newQuestion = new Question(title, description, category, nowTime, globalClass.getLoggedInUser().KEY);
//
//                        dbHelper.updateObject(globalClass.REF_QUESTIONS + "/" + question.questionCategory + "/" + question.KEY, null);
//                        dbHelper.updateObject(globalClass.REF_QUESTIONS + "/" + category + "/" + question.KEY, newQuestion);
//
//                        dialog.dismiss();
//                        Question newQuestionWithKey = newQuestion.setKey(question.KEY);
//                        globalClass.setSelectedQuestion(newQuestionWithKey);
//                        parentActivity.recreate();
//                    }
//                });
//                break;
//            case MODE_DELETE:
//                dialog.setTitle(parentActivity.getString(R.string.dialog_delete_question_title));
//                txtTitle.setText(question.questionTitle);
//                txtDescription.setText(question.questionDescription);
//                spnCategory.setSelection(adapter.getPosition(question.questionCategory));
//
//                txtTitle.setEnabled(false);
//                txtDescription.setEnabled(false);
//                spnCategory.setEnabled(false);
//
//                dialog.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dbHelper.updateObject(globalClass.REF_QUESTIONS + "/" + question.questionCategory + "/" + question.KEY, null);
//                        dbHelper.updateObject(globalClass.REF_ANSWERS + "/" + question.KEY, null);
//                        dialog.dismiss();
//                        parentActivity.finish();
//                    }
//                });
//                break;
//            case MODE_HIDE:
//                dialog.setTitle(parentActivity.getString(R.string.dialog_hide_question_title));
//                txtTitle.setText(question.questionTitle);
//                txtDescription.setText(question.questionDescription);
//                spnCategory.setSelection(adapter.getPosition(question.questionCategory));
//
//                txtTitle.setEnabled(false);
//                txtDescription.setEnabled(false);
//                spnCategory.setEnabled(false);
//
//                dialog.setPositiveButton(R.string.action_hide, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dbHelper.updateObject(globalClass.REF_QUESTIONS + "/" + question.questionCategory + "/" + question.KEY + "/" + globalClass.REF_SHOW, false);
//                        dialog.dismiss();
//                        parentActivity.recreate();
//                    }
//                });
//                break;
//            case MODE_SHOW:
//                dialog.setTitle(parentActivity.getString(R.string.dialog_show_question_title));
//                txtTitle.setText(question.questionTitle);
//                txtDescription.setText(question.questionDescription);
//                spnCategory.setSelection(adapter.getPosition(question.questionCategory));
//
//                txtTitle.setEnabled(false);
//                txtDescription.setEnabled(false);
//                spnCategory.setEnabled(false);
//
//                dialog.setPositiveButton(R.string.action_show, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dbHelper.updateObject(globalClass.REF_QUESTIONS + "/" + question.questionCategory + "/" + question.KEY + "/" + globalClass.REF_SHOW, true);
//                        dialog.dismiss();
//                        parentActivity.recreate();
//                    }
//                });
//                break;
//            case MODE_REPORT:
//                dialog.setTitle(parentActivity.getString(R.string.dialog_report_question_title));
//                txtTitle.setText(question.questionTitle);
//                txtDescription.setText(question.questionDescription);
//                spnCategory.setSelection(adapter.getPosition(question.questionCategory));
//
//                txtTitle.setEnabled(false);
//                txtDescription.setEnabled(false);
//                spnCategory.setEnabled(false);
//
//                dialog.setPositiveButton(R.string.action_report, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(final DialogInterface dialog, int which) {
//                        final String path = globalClass.REF_QUESTIONS + "/" + question.questionCategory + "/" + question.KEY + "/" + globalClass.REF_REPORT;
//                        Query query = dbHelper.getReference(path);
//                        ValueEventListener valueEventListener = new ValueEventListener() {
//                            @Override
//                            public void onDataChange(DataSnapshot dataSnapshot) {
//                                if(dataSnapshot.getChildrenCount() == Question.MAX_REPORT_COUNT-1){
//                                    dbHelper.updateObject(globalClass.REF_QUESTIONS + "/" + question.questionCategory + "/" + question.KEY + "/" + globalClass.REF_SHOW, false);
//                                }
//                                dbHelper.pushObject(path, globalClass.getLoggedInUser().KEY);
//                                dialog.dismiss();
//                                globalClass.setSelectedQuestion(null);
//                                parentActivity.recreate();
//                            }
//
//                            @Override
//                            public void onCancelled(DatabaseError databaseError) {
//                                Log.d(globalClass.TAG, "Questions failed to retrieve", databaseError.toException());
//                            }
//                        };
//                        query.addListenerForSingleValueEvent(valueEventListener);
//                    }
//                });
//                break;
//        }
//
//
//        dialog.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//
//        dialog.show();
//
//    }
//
//    public static void showAnswerManageDialog(int mode, final AppCompatActivity parentActivity, final Question question, final Answer answer) {
//        dbHelper = new DBHelper();
//        globalClass = (GlobalClass) parentActivity.getApplicationContext();
//        inflater = parentActivity.getLayoutInflater();
//        content = inflater.inflate(R.layout.dialog_add_answer, null);
//        dialog = new AlertDialog.Builder(new ContextWrapper(parentActivity), R.style.AppTheme_Dialog);
//        dialog.setView(content);
//
//        final EditText txtDescription = content.findViewById(R.id.txtDialogAnswer);
//
//        switch (mode) {
//            case MODE_ADD:
//                dialog.setTitle(parentActivity.getString(R.string.dialog_add_new_answer_title));
//                dialog.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        String txtAnswer = txtDescription.getText().toString();
//                        Long nowTime = System.currentTimeMillis();
//
//                        Answer answer = new Answer(txtAnswer, nowTime, globalClass.getLoggedInUser().KEY);
//
//                        dbHelper.pushObject(globalClass.REF_ANSWERS + "/"
//                                + question.KEY, answer);
//                        Toast.makeText(parentActivity,
//                                "Answer Added successfully",
//                                Toast.LENGTH_LONG).show();
//                        dialog.dismiss();
//                    }
//                });
//                break;
//            case MODE_UPDATE:
//                dialog.setTitle(parentActivity.getString(R.string.dialog_update_answer_title));
//
//                txtDescription.setText(answer.answerDescription);
//
//                dialog.setPositiveButton(R.string.update, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        String txtAnswer = txtDescription.getText().toString();
//                        Long nowTime = System.currentTimeMillis();
//
//                        Answer newAnswer = new Answer(txtAnswer, nowTime, globalClass.getLoggedInUser().KEY);
//
//                        dbHelper.updateObject(globalClass.REF_ANSWERS + "/"
//                                + question.KEY + "/" + answer.KEY , newAnswer);
//                        Toast.makeText(parentActivity,
//                                "Answer Updated successfully",
//                                Toast.LENGTH_LONG).show();
//                        dialog.dismiss();
//                    }
//                });
//                break;
//            case MODE_DELETE:
//                dialog.setTitle(parentActivity.getString(R.string.dialog_delete_answer_title));
//
//                txtDescription.setText(answer.answerDescription);
//                txtDescription.setEnabled(false);
//
//                dialog.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dbHelper.updateObject(globalClass.REF_ANSWERS + "/"
//                                + question.KEY + "/" + answer.KEY, null);
//                        Toast.makeText(parentActivity,
//                                "Answer Deleted successfully",
//                                Toast.LENGTH_LONG).show();
//                        dialog.dismiss();
//                    }
//                });
//                break;
//            case MODE_HIDE:
//                dialog.setTitle(parentActivity.getString(R.string.dialog_hide_answer_title));
//
//                txtDescription.setText(answer.answerDescription);
//                txtDescription.setEnabled(false);
//
//                dialog.setPositiveButton(R.string.action_hide, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dbHelper.updateObject(globalClass.REF_ANSWERS + "/"
//                                + question.KEY + "/" + answer.KEY+ "/" + globalClass.REF_SHOW, false);
//                        dialog.dismiss();
//                    }
//                });
//                break;
//            case MODE_SHOW:
//                dialog.setTitle(parentActivity.getString(R.string.dialog_show_answer_title));
//
//                txtDescription.setText(answer.answerDescription);
//                txtDescription.setEnabled(false);
//
//                dialog.setPositiveButton(R.string.action_show, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dbHelper.updateObject(globalClass.REF_ANSWERS + "/"
//                                + question.KEY + "/" + answer.KEY+ "/" + globalClass.REF_SHOW, true);
//                        dialog.dismiss();
//                    }
//                });
//                break;
//            case MODE_REPORT:
//                dialog.setTitle(parentActivity.getString(R.string.dialog_report_answer_title));
//
//                txtDescription.setText(answer.answerDescription);
//                txtDescription.setEnabled(false);
//
//                dialog.setPositiveButton(R.string.action_report, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(final DialogInterface dialog, int which) {
//                        final String path = globalClass.REF_ANSWERS + "/"
//                                + question.KEY + "/" + answer.KEY+ "/" + globalClass.REF_REPORT;
//                        Query query = dbHelper.getReference(path);
//                        ValueEventListener valueEventListener = new ValueEventListener() {
//                            @Override
//                            public void onDataChange(DataSnapshot dataSnapshot) {
//                                if(dataSnapshot.getChildrenCount() == Answer.MAX_REPORT_COUNT-1){
//                                    dbHelper.updateObject(globalClass.REF_ANSWERS + "/"
//                                            + question.KEY + "/" + answer.KEY+ "/" + globalClass.REF_SHOW, false);
//                                }
//                                dbHelper.pushObject(path, globalClass.getLoggedInUser().KEY);
//                                dialog.dismiss();
//                                parentActivity.recreate();
//                            }
//
//                            @Override
//                            public void onCancelled(DatabaseError databaseError) {
//                                Log.d(globalClass.TAG, "Questions failed to retrieve", databaseError.toException());
//                            }
//                        };
//                        query.addListenerForSingleValueEvent(valueEventListener);
//                    }
//                });
//                break;
//        }
//
//
//        dialog.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//
//        dialog.show();
//
//    }
}
