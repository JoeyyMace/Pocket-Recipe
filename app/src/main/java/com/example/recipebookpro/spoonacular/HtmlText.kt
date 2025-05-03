package com.example.recipebookpro.spoonacular

import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import com.example.myapplication.R

@Composable
fun InstructionText(instructionHtml: String) {
    AndroidView(
        factory = { context ->
            TextView(context).apply {
                movementMethod = LinkMovementMethod.getInstance()
                text = HtmlCompat.fromHtml(instructionHtml, HtmlCompat.FROM_HTML_MODE_LEGACY)
                linksClickable = true
                setLinkTextColor(ContextCompat.getColor(context, R.color.purple_500)) // optional
            }
        },
        update = {
            it.text = HtmlCompat.fromHtml(instructionHtml, HtmlCompat.FROM_HTML_MODE_LEGACY)
        }
    )
}
