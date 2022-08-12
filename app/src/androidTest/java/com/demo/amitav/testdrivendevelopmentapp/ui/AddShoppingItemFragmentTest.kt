package com.demo.amitav.testdrivendevelopmentapp.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.demo.amitav.testdrivendevelopmentapp.R
import com.demo.amitav.testdrivendevelopmentapp.data.local.ShoppingItem
import com.demo.amitav.testdrivendevelopmentapp.getOrAwaitValue
import com.demo.amitav.testdrivendevelopmentapp.launchFragmentInHiltContainer
import com.demo.amitav.testdrivendevelopmentapp.repositories.FakeShoppingRepositoryAndroidTest
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import javax.inject.Inject

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class AddShoppingItemFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Mock
    lateinit var navController: NavController

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var fragmentFactory: ShoppingFragmentFactory

    private lateinit var testViewModel: ShoppingViewModel

    @Before
    fun setup() {
        hiltRule.inject()
        MockitoAnnotations.initMocks(this)
        testViewModel = ShoppingViewModel(FakeShoppingRepositoryAndroidTest())
    }

    @Test
    fun pressBackButton_popBackStack() {
        launchFragmentInHiltContainer<AddShoppingItemFragment>(
            fragmentFactory = fragmentFactory
        ) {
            Navigation.setViewNavController(requireView(), navController)
        }
        pressBack()
        verify(navController).popBackStack()
    }

    @Test
    fun clickAddImageItemButton_navigateToImagePickFragment() {
        launchFragmentInHiltContainer<AddShoppingItemFragment>(
            fragmentFactory = fragmentFactory
        ) {
            Navigation.setViewNavController(requireView(), navController)
        }
        Espresso.onView(ViewMatchers.withId(R.id.ivShoppingImage)).perform(ViewActions.click())
        verify(navController).navigate(
            AddShoppingItemFragmentDirections.actionAddShoppingItemFragmentToImagePickFragment()
        )
    }

    @Test
    fun clickInsertIntoDb_shoppingItemInsertedIntoDb() {
        launchFragmentInHiltContainer<AddShoppingItemFragment>(
            fragmentFactory = fragmentFactory
        ) {
            viewModel = testViewModel
        }
        Espresso.onView(withId(R.id.etShoppingItemName))
            .perform(ViewActions.replaceText("shopping item"))
        Espresso.onView(withId(R.id.etShoppingItemAmount)).perform(ViewActions.replaceText("5"))
        Espresso.onView(withId(R.id.etShoppingItemPrice)).perform(ViewActions.replaceText("5.5"))
        Espresso.onView(withId(R.id.btnAddShoppingItem)).perform(ViewActions.click())

        assertThat(testViewModel.shoppingItems.getOrAwaitValue())
            .contains(ShoppingItem("shopping item", 5, 5.5f, ""))
    }
}















