package com.example.ucp2.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ucp2.ui.view.Barang.DetailBarangView
import com.example.ucp2.ui.view.Barang.HomeBarangView
import com.example.ucp2.ui.view.Barang.InsertBrgView
import com.example.ucp2.ui.view.Barang.UpdateBarangView
import com.example.ucp2.ui.view.HomeTokoView
import com.example.ucp2.ui.view.Supplier.HomeSupplierView
import com.example.ucp2.ui.view.Supplier.InsertSplView

@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route
    )
    {
        composable(
            route = DestinasiHome.route
        ) {
            HomeTokoView(
                onSuplierClick = {
                    navController.navigate(DestinasiHomeSupplier.route)
                },
                onAddSplClick = {
                    navController.navigate(DestinasiInsertSupplier.route)
                },
                onBarangClick = {
                    navController.navigate(DestinasiHomeBarang.route)
                },
                onAddBrgClick = {
                    navController.navigate(DestinasiInsertBarang.route)
                },
                modifier = Modifier
            )
        }

        composable(
            route = DestinasiHomeSupplier.route
        ) {
            HomeSupplierView(
                onAddSplClick = {
                    navController.navigate(DestinasiInsertSupplier.route)
                },
                onBackArrow = {
                    navController.popBackStack()
                },
                modifier = Modifier
            )

            //diisi

        }
        composable(
            route = DestinasiInsertSupplier.route
        ) {
            InsertSplView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = Modifier
            )

        }
        composable(
            route = DestinasiHomeBarang.route
        ) {
            HomeBarangView(
                onAddBarangClick = {
                    navController.navigate(DestinasiInsertBarang.route)
                },
                onBackArrow = {
                    navController.popBackStack()
                },
                onDetailBrgClick = {
                    navController.navigate(DestinasiDetailBarang.route)
                },
                modifier = Modifier
            )
        }
        composable(
            route = DestinasiInsertBarang.route
        ) {
            InsertBrgView(
                onBackArrow = {
                    navController.popBackStack()
                },
                onNavigate =
                {
                    navController.popBackStack()
                },
                modifier = Modifier
            )
        }
        composable(
            DestinasiUpdateBarang.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailBarang.idBrg) {
                    type = NavType.StringType
                }
            )
        ) {
            UpdateBarangView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = Modifier
            )
            composable(
                DestinasiDetailBarang.routesWithArg,
                arguments = listOf(
                    navArgument(DestinasiDetailBarang.idBrg) {
                        type = NavType.StringType
                    }
                )
            ) {
                val idBarang = it.arguments?.getString(DestinasiDetailBarang.idBrg)
                idBarang?.let { idBarang ->
                    DetailBarangView(
                        onBack = {
                            navController.popBackStack()
                        },
                        onEditClick = {
                            navController.navigate("${DestinasiUpdateBarang.route}/$it")
                        },
                        onDeleteClick = {
                            navController.popBackStack()
                        },
                        modifier = Modifier
                    )
                }
            }
        }
    }
}