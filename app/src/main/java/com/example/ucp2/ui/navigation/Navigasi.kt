package com.example.ucp2.ui.navigation


interface HalamanController {
    val route : String
}

object DestinasiHome : HalamanController {
    override val route = "home"
}

object DestinasiHomeSupplier : HalamanController {
    override val route = "supplier"
}

object DestinasiInsertSupplier : HalamanController {
    override val route = "supplier/add"
}


object DestinasiHomeBarang : HalamanController {
    override val route = "barang"
}

object DestinasiInsertBarang : HalamanController {
    override val route = "barang/add"
}

object DestinasiDetailBarang : HalamanController {
    override val route = "barang"
    const val idBrg = "id"
    val routesWithArg = "$route/{$idBrg}"
}

object DestinasiUpdateBarang : HalamanController {
    override val route = "updateBrg"
    const val idBrg = "id"
    val routesWithArg = "$route/{$idBrg}"
}