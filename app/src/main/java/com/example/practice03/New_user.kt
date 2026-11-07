package com.example.practice03

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class New_user : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var passwordEditText2: EditText
    private lateinit var newUserBtn: Button
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_user)

        firebaseAuth = FirebaseAuth.getInstance()

        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        passwordEditText2 = findViewById(R.id.passwordEditText2)
        newUserBtn = findViewById(R.id.new_user_btn)

        newUserBtn.setOnClickListener {
            val email = emailEditText.text.toString()
            val pass = passwordEditText.text.toString()
            val confirmPass = passwordEditText2.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty()) {
                if (pass == confirmPass) {
                    if (pass.length >= 6) { // 수정: 비밀번호 길이가 6자리 이상인지 확인
                        firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(this, "회원가입이 완료되었습니다. 다시 로그인해주세요.", Toast.LENGTH_SHORT).show()
                                firebaseAuth.signOut() // 추가: 사용자 로그아웃
                                val intent = Intent(this, LoginPage::class.java)
                                startActivity(intent)
                                finish() // 현재 액티비티 종료
                            } else {
                                Toast.makeText(this, "회원가입에 실패했습니다.", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        Toast.makeText(this, "비밀번호를 6자리이상 입력해주세요.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "빈 칸을 모두 채워주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
