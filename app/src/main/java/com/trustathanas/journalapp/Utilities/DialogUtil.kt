import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.support.v7.app.AlertDialog
import com.trustathanas.journalapp.App
import com.trustathanas.journalapp.R

/**
 * A utility class wrapping all the functionality of the Dialog class
 * this provides a way to manage the theme of the dialog in one place as well
 * as all the types of dialogs you choose to display in the application.
 * <p>
 * NOTE: this is a subset of the options available from the Dialog#Builder
 */
object DialogUtil {

    /**
     * Dialog with multiple options but a single choice
     *
     * @param context          current context
     * @param title            dialog title
     * @param currentSelection dialog current selection
     * @param options          list of options
     * @param optionsListener  listener for when an option is selected
     * @return the AlertDialog
     */
    private fun createSingleChoiceDialog(context: Context, title: String, currentSelection: Int,
                                         options: Array<String>,
                                         optionsListener: DialogInterface.OnClickListener): AlertDialog {

        return AlertDialog.Builder(context, R.style.AppTheme_Dialog)
                .setTitle(title)
                .setSingleChoiceItems(options, currentSelection, optionsListener)
                .create()

    }

    /**
     * Dialog with one button a title and a message
     *
     * @param context            current context
     * @param title              dialog title
     * @param message            dialog message
     * @param positiveButtonText positive button text
     * @param positiveListener   positive button listener
     * @return the AlertDialog
     */
    fun createAlertDialogWithOneButton(context: Context, title: String, message: String,
                                       positiveButtonText: String,
                                       positiveListener: DialogInterface.OnClickListener): AlertDialog {

        return AlertDialog.Builder(context, R.style.AppTheme_Dialog)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveButtonText, positiveListener)
                .create()

    }

    /**
     * Dialog with two buttons a title and a message
     *
     * @param context            current context
     * @param title              dialog title
     * @param message            dialog message
     * @param positiveButtonText positive button text
     * @param positiveListener   positive button listener
     * @param negativeButtonText negative button text
     * @param negativeListener   negative button listener
     * @return the AlertDialog
     */
    fun createAlertDialogWithTwoButtons(context: Context, title: String, message: String,
                                        positiveButtonText: String,
                                        positiveListener: DialogInterface.OnClickListener,
                                        negativeButtonText: String,
                                        negativeListener: DialogInterface.OnClickListener): AlertDialog {

        return AlertDialog.Builder(context, android.R.style.Theme_Holo_Light_Dialog) //AppTheme_Dialog
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveButtonText, positiveListener)
                .setNegativeButton(negativeButtonText, negativeListener)
                .create()
    }

    /**
     * Dialog with one button a title and a message that stores a preference
     *
     * @param context            current context
     * @param title              dialog title
     * @param message            dialog message
     * @return the AlertDialog
     */
    fun createTermsOfServiceDialog(context: Context, title: String, message: String): AlertDialog {

        return AlertDialog.Builder(context, R.style.AppTheme_Dialog)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(context.getText(R.string.accept)) { dialog, which ->
                    App.preferences.tsAndCs = true
                }
                .create()

    }
}