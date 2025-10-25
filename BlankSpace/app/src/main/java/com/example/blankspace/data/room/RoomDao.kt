package com.example.blankspace.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomDao {
    @Query("SELECT * FROM zanr")
    fun getZanrovi(): Flow<List<ZanrEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(zanr: ZanrEntity)

    @Query("DELETE FROM zanr")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(zanrovi: List<ZanrEntity>)

    //izvodjac
    @Query("SELECT * FROM izvodjac")
    fun getIzvodjaci(): Flow<List<IzvodjacEntity>>

    @Query("DELETE FROM izvodjac")
    suspend fun deleteAllIzvodjac()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllIzvodjac(izvodjaci: List<IzvodjacEntity>)

    //pesme
    @Query("SELECT * FROM pesma")
    fun getPesme(): Flow<List<PesmaEntity>>

    @Query("DELETE FROM pesma")
    suspend fun deleteAllPesme()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPesme(pesme: List<PesmaEntity>)

    //stihovi
    @Query("SELECT * FROM stihovi")
    fun getStihovi(): Flow<List<StihoviEntity>>

    @Query("DELETE FROM stihovi")
    suspend fun deleteAllStihovi()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllStihovi(pesme: List<StihoviEntity>)

    @Query("SELECT * FROM stihovi WHERE nivo = :tezina")
    suspend fun getStihoviPoTezini(tezina: String): List<StihoviEntity>

    @Query("SELECT * FROM pesma WHERE id = :id")
    suspend fun getPesmaPoId(id: Int): PesmaEntity

    @Query("SELECT * FROM izvodjac WHERE id = :id")
    suspend fun getIzvodjacPoId(id: Int): IzvodjacEntity

    @Query("""
    SELECT s.* FROM stihovi s
    INNER JOIN pesma p ON s.pes_id = p.id
    INNER JOIN izvodjac i ON p.izv_id = i.id
    WHERE s.nivo = :nivo AND i.zan_id IN (:zanrovi)
""")
    suspend fun getStihoviPoTeziniIZanrovima(nivo: String, zanrovi: List<Int>): List<StihoviEntity>
}