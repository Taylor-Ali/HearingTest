package com.leaf.repository.mapper

/**
 * Mapper
 * interface use to facilitate model mapping between modules.
 * @param DataClass represents the data model likely from cache or remote module.
 * @param Model represents the model from the repository layer.
 */
interface Mapper<DataClass, Model> {

    /**
     * mapFromModel
     * maps data from the repository model to the data model
     */
    fun mapFromModel(type: Model): DataClass

    /**
     * mapToModel
     * maps data to the repository model to the data model
     */
    fun mapToModel(type: DataClass): Model
}