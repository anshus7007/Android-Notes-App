package com.anshu.dynamicnotes.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.anshu.dynamicnotes.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class NotepadBottomSheet: BottomSheetDialogFragment() {
    private var tvName: TextView? = null
    private var listener: Listener? = null
    private var name: String? = null
    fun setListener(listener: Listener) {
        this.listener = listener
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.bottom_sheet_notepad, container, false)
//        rename.setOnClickListener {
//            listener!!.onRenameClicked()
//            dismiss()
//        }
//            val favourite: ConstraintLayout = view.findViewById(R.id.fav)
//            favourite.setOnClickListener {
//                listener!!.onFavClicked(!isFavourite)
//                dismiss()
//            }
//        val delete: ConstraintLayout = view.findViewById(R.id.delete)
//        delete.setOnClickListener {
//            listener!!.onDeleteClicked()
//            dismiss()
//        }
        //        ConstraintLayout download = view.findViewById(R.id.download);
//        download.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                listener.onDownloadClicked();
//                dismiss();
//            }
//        });
//        val lockLayout: ConstraintLayout = view.findViewById(R.id.lock)
//        lockLayout.setOnClickListener { v: View? -> listener!!.onFolderLockClicked() }
//        val share: ConstraintLayout = view.findViewById(R.id.share)
//        share.setOnClickListener {
//            listener!!.onShareClicked()
//            dismiss()
//        }
//            val move: ConstraintLayout = view.findViewById(R.id.move)
//            move.setOnClickListener {
//                listener!!.onMoveClicked()
//                dismiss()
//            }
//            tvFav = view.findViewById(R.id.tvFav)
//            ivStar = view.findViewById(R.id.imageView13)
        val ivCancel = view.findViewById<ImageView>(R.id.imageView10)
//        tvName = view.findViewById<TextView>(R.id.title)
        ivCancel.setOnClickListener { dismiss() }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleArguments()
//            handleFav()
    }

//        private fun handleFav() {
//            if (isFavourite) {
//                tvFav.setText(R.string.remvoe_fav)
//                ivStar!!.setImageDrawable(resources.getDrawable(R.drawable.ic_star_sel))
//            } else {
//                tvFav.setText(R.string.fav)
//                ivStar!!.setImageDrawable(resources.getDrawable(R.drawable.ic_star_unsel))
//            }
//        }

    private fun handleArguments() {
        val bundle = arguments
//            name = bundle!!.getString(Constants.NAME)
//        tvName!!.text = name
//            isFavourite = bundle.getBoolean(Constants.IS_FAV)
//            val documentName = bundle.getString(Constants.DOCUMENT_NAME)
        //     File file = new File((requireContext().getExternalFilesDir((String) null) + "/" + Constants.DEFAULT_FOLDER_NAME + "/" + documentName) + "/" + documentName + ".pdf");
        // generatePdfText.setText("View PDF");
//        if (file.exists()) {
//            setPdfListener(true, file);
//        } else {
//            setPdfListener(false, null);
//        }
//            setPdfListener(false, null)
    }

//        private fun setPdfListener(isPdfAvailable: Boolean, file: File?) {
//            generatePdfText!!.setOnClickListener { v: View? ->
//                if (isPdfAvailable) {
//                    startActivity(PdfActivity.newInstance(requireContext(), file, name))
//                } else {
//                    listener!!.onDownloadClicked()
//                }
//                dismiss()
//            }
//        }

    interface Listener {
//        fun onRenameClicked()
//        fun onDeleteClicked()
//        fun onShareClicked()
//        fun onFolderLockClicked()
    }

    companion object {
        fun newInstance(): NotepadBottomSheet {
//                val args = Bundle()
//                args.putString(Constants.NAME, name)
//                args.putBoolean(Constants.IS_FAV, isFavourite)
//                args.putString(Constants.DOCUMENT_NAME, displayName)
            val fragment = NotepadBottomSheet()
            return fragment
        }
    }

}