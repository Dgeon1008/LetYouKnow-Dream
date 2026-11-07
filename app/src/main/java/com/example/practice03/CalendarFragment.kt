package com.example.practice03

import android.content.Intent
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practice03.databinding.CalendarfragmentBinding
import java.io.FileInputStream
import java.io.FileOutputStream

class CalendarFragment : Fragment() {

    private var fname: String = ""
    private var str: String = ""
    private var _binding: CalendarfragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var benefitAdapter: BenefitAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CalendarfragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        // binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
        //     binding.diaryTextView.visibility = View.VISIBLE
        //     binding.saveBtn.visibility = View.VISIBLE
        //     binding.contextEditText.visibility = View.VISIBLE
        //     binding.textView2.visibility = View.INVISIBLE
        //     binding.chaBtn.visibility = View.INVISIBLE
        //     binding.delBtn.visibility = View.INVISIBLE

        //     binding.diaryTextView.text = String.format("%d / %d / %d", year, month + 1, dayOfMonth)
        //     binding.contextEditText.setText("")
        //     checkedDay(year, month, dayOfMonth)
        // }

        // binding.saveBtn.setOnClickListener {
        //     saveDiary(fname)
        //     showToast("$fname 데이터를 저장했습니다.")
        //     str = binding.contextEditText.text.toString()
        //     binding.textView2.text = str
        //     binding.saveBtn.visibility = View.INVISIBLE
        //     binding.chaBtn.visibility = View.VISIBLE
        //     binding.delBtn.visibility = View.VISIBLE
        //     binding.contextEditText.visibility = View.INVISIBLE
        //     binding.textView2.visibility = View.VISIBLE
        // }

        // RecyclerView 초기화
        val recyclerView: RecyclerView = view.findViewById(R.id.favoriteRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // 아이템 클릭 이벤트 처리: 상세 페이지로 이동
        val onItemClick: (Benefit) -> Unit = { benefit ->
            val intent = when (benefit.type) {
                "employment" -> Intent(requireContext(), EmploymentDetailActivity::class.java)
                "life" -> Intent(requireContext(), LifeDetailActivity::class.java)
                "travel" -> Intent(requireContext(), TravelDetailActivity::class.java)
                "money" -> Intent(requireContext(), MoneyDetailActivity::class.java)
                else -> null
            }
            if (intent != null) {
                intent.putExtra("benefit", benefit.documentId) // 필요한 데이터를 Intent에 추가
                startActivity(intent)
            }
        }

        benefitAdapter = BenefitAdapter(emptyList(), onItemClick)
        recyclerView.adapter = benefitAdapter

        // 즐겨찾기한 혜택 로드
        loadFavoriteBenefits()

        return view
    }

    private fun loadFavoriteBenefits() {
        val favoriteBenefits = FavoriteBenefitsManager.getFavoriteBenefits()
        benefitAdapter.updateBenefits(favoriteBenefits)
    }

    // private fun checkedDay(cYear: Int, cMonth: Int, cDay: Int) {
    //     fname = "$cYear-${cMonth + 1}-$cDay.txt"
    //     var fis: FileInputStream? = null

    //     try {
    //         fis = activity?.openFileInput(fname)
    //         val fileData = ByteArray(fis?.available() ?: 0)
    //         fis?.read(fileData)
    //         fis?.close()

    //         str = String(fileData)

    //         binding.contextEditText.visibility = View.INVISIBLE
    //         binding.textView2.visibility = View.VISIBLE
    //         binding.textView2.text = str

    //         binding.saveBtn.visibility = View.INVISIBLE
    //         binding.chaBtn.visibility = View.VISIBLE
    //         binding.delBtn.visibility = View.VISIBLE

    //         binding.chaBtn.setOnClickListener {
    //             binding.contextEditText.visibility = View.VISIBLE
    //             binding.textView2.visibility = View.INVISIBLE
    //             binding.contextEditText.setText(str)
    //             binding.saveBtn.visibility = View.VISIBLE
    //             binding.chaBtn.visibility = View.INVISIBLE
    //             binding.delBtn.visibility = View.INVISIBLE
    //             binding.textView2.text = binding.contextEditText.text.toString()
    //         }

    //         binding.delBtn.setOnClickListener {
    //             binding.textView2.visibility = View.INVISIBLE
    //             binding.contextEditText.setText("")
    //             binding.contextEditText.visibility = View.VISIBLE
    //             binding.saveBtn.visibility = View.VISIBLE
    //             binding.chaBtn.visibility = View.INVISIBLE
    //             binding.delBtn.visibility = View.INVISIBLE
    //             removeDiary(fname)
    //             showToast("$fname 데이터를 삭제했습니다.")
    //         }

    //         if (binding.textView2.text.isEmpty()) {
    //             binding.textView2.visibility = View.INVISIBLE
    //             binding.diaryTextView.visibility = View.VISIBLE
    //             binding.saveBtn.visibility = View.VISIBLE
    //             binding.chaBtn.visibility = View.INVISIBLE
    //             binding.delBtn.visibility = View.INVISIBLE
    //             binding.contextEditText.visibility = View.VISIBLE
    //         }

    //     } catch (e: Exception) {
    //         e.printStackTrace()
    //     }
    // }

    // @SuppressLint("WrongConstant")
    // private fun saveDiary(readyDay: String) {
    //     var fos: FileOutputStream? = null

    //     try {
    //         fos = activity?.openFileOutput(readyDay, AppCompatActivity.MODE_NO_LOCALIZED_COLLATORS)
    //         val content: String = binding.contextEditText.text.toString()
    //         fos?.write(content.toByteArray())
    //         fos?.close()
    //     } catch (e: Exception) {
    //         e.printStackTrace()
    //     }
    // }

    // @SuppressLint("WrongConstant")
    // private fun removeDiary(readyDay: String) {
    //     var fos: FileOutputStream? = null

    //     try {
    //         fos = activity?.openFileOutput(readyDay, AppCompatActivity.MODE_NO_LOCALIZED_COLLATORS)
    //         val content = ""
    //         fos?.write(content.toByteArray())
    //         fos?.close()
    //     } catch (e: Exception) {
    //         e.printStackTrace()
    //     }
    // }

    //    private fun showToast(message: String) {
//        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
//    }
//
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
