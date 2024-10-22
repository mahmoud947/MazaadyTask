package com.example.data.datasource.remote

import com.example.core.base.BaseResponse
import com.example.data.datasource.dtos.category.CategoryRes
import com.example.data.datasource.dtos.properties.PropertiesDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CategoryService {

    @GET("get_all_cats")
    suspend fun getAllCategory(): BaseResponse<CategoryRes>

    @GET("properties")
    suspend fun getSubCategories(
        @Query("cat")
        categoryId: Int
    ): BaseResponse<List<PropertiesDto>>

    @GET("get-options-child/{id}")
    suspend fun getOptionsChildByParentId(
        @Path("id")
        parentId: Int
    ): BaseResponse<List<PropertiesDto>>

}