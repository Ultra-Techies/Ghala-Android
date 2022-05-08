package com.ultratechies.ghala.ui.inventory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ultratechies.ghala.data.models.responses.inventory.InventoryResponseItem
import com.ultratechies.ghala.data.repository.APIResource
import com.ultratechies.ghala.data.repository.InventoryRepository
import com.ultratechies.ghala.utils.parseErrors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InventoryViewModel @Inject constructor(val inventoryRepo: InventoryRepository) : ViewModel() {

    private val _fetchInventory = MutableSharedFlow<List<InventoryResponseItem>>()
    val fetchInventory = _fetchInventory.asSharedFlow()

    private val _deleteInventory = MutableSharedFlow<Any>()
    val deleteInventory = _deleteInventory.asSharedFlow()

    private val _errorMessage = MutableSharedFlow<String>()
    val errorMessage = _errorMessage.asSharedFlow()

    fun fetchInventory() {
        viewModelScope.launch {
            val response = inventoryRepo.getInventory()
            when (response) {
                is APIResource.Success -> {
                    _fetchInventory.emit(response.value.reversed())
                }
                is APIResource.Loading -> {

                }
                is APIResource.Error -> {
                    _errorMessage.emit(parseErrors(response))
                }
            }
        }
    }

    fun deleteInventory(sku: Int) {
        viewModelScope.launch {
            val response = inventoryRepo.deleteInventoryItem(sku)
            when (response) {
                is APIResource.Success -> {
                    _deleteInventory.emit(response.value)
                }
                is APIResource.Error -> {
                    _errorMessage.emit(parseErrors(response))
                }
                else->{

                }
            }
        }
    }


}