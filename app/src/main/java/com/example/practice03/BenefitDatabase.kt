package com.example.practice03

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [BenefitEntity::class], version = 2) // 버전을 2로 업데이트
abstract class BenefitDatabase : RoomDatabase() {

    abstract fun benefitDao(): BenefitDao

    companion object {
        @Volatile
        private var INSTANCE: BenefitDatabase? = null

        fun getDatabase(context: Context): BenefitDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BenefitDatabase::class.java,
                    "benefit_database"
                ).fallbackToDestructiveMigration() // 마이그레이션 작업이 필요한 경우 데이터를 삭제하고 새로 생성
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
