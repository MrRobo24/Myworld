package com.example.myworld.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myworld.R
import com.example.myworld.adapter.MainRecycleViewAdapter
import com.example.myworld.model.AllCategory
import com.example.myworld.model.CategoryItem
import kotlinx.android.synthetic.main.fragment_search.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment()
{
    private var mainCategoryRecycler : RecyclerView? = null
    private var mainRecyclerAdaptor : MainRecycleViewAdapter? = null
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onStart()
    {
        val categoryItemList: MutableList<CategoryItem> = ArrayList()
        categoryItemList.add(CategoryItem(1, "category1"))
        categoryItemList.add(CategoryItem(1, "category2"))
        categoryItemList.add(CategoryItem(1, "category3"))
        categoryItemList.add(CategoryItem(1, "category4"))
        categoryItemList.add(CategoryItem(1, "category5"))

        val categoryItemList2: MutableList<CategoryItem> = ArrayList()
        categoryItemList2.add(CategoryItem(2, "category1"))
        categoryItemList2.add(CategoryItem(2, "category2"))
        categoryItemList2.add(CategoryItem(2,  "category3"))
        categoryItemList2.add(CategoryItem(2, "category4"))
        categoryItemList2.add(CategoryItem(2, "category5"))


        val categoryItemList3: MutableList<CategoryItem> = ArrayList()
        categoryItemList3.add(CategoryItem(2, "category1"))
        categoryItemList3.add(CategoryItem(2, "category2"))
        categoryItemList3.add(CategoryItem(2, "category3"))
        categoryItemList3.add(CategoryItem(2, "category4"))
        categoryItemList3.add(CategoryItem(2, "category5"))

        val categoryItemList4: MutableList<CategoryItem> = ArrayList()
        categoryItemList4.add(CategoryItem(2, "category1"))
        categoryItemList4.add(CategoryItem(2, "category2"))
        categoryItemList4.add(CategoryItem(2, "category3"))
        categoryItemList4.add(CategoryItem(2, "category4"))
        categoryItemList4.add(CategoryItem(2, "category5"))

        val categoryItemList5: MutableList<CategoryItem> = ArrayList()
        categoryItemList4.add(CategoryItem(2, "category1"))
        categoryItemList4.add(CategoryItem(2, "category2"))
        categoryItemList4.add(CategoryItem(2, "category3"))
        categoryItemList4.add(CategoryItem(2, "category4"))
        categoryItemList4.add(CategoryItem(2, "category5"))

        val categoryItemList6: MutableList<CategoryItem> = ArrayList()
        categoryItemList4.add(CategoryItem(2, "category1"))
        categoryItemList4.add(CategoryItem(2, "category2"))
        categoryItemList4.add(CategoryItem(2, "category3"))
        categoryItemList4.add(CategoryItem(2, "category4"))
        categoryItemList4.add(CategoryItem(2, "category5"))

        val categoryItemList7: MutableList<CategoryItem> = ArrayList()
        categoryItemList4.add(CategoryItem(2, "category1"))
        categoryItemList4.add(CategoryItem(2, "category2"))
        categoryItemList4.add(CategoryItem(2, "category3"))
        categoryItemList4.add(CategoryItem(2, "category4"))
        categoryItemList4.add(CategoryItem(2, "category5"))

        val categoryItemList8: MutableList<CategoryItem> = ArrayList()
        categoryItemList4.add(CategoryItem(2, "category1"))
        categoryItemList4.add(CategoryItem(2, "category2"))
        categoryItemList4.add(CategoryItem(2, "category3"))
        categoryItemList4.add(CategoryItem(2, "category4"))
        categoryItemList4.add(CategoryItem(2, "category5"))

        val categoryItemList9: MutableList<CategoryItem> = ArrayList()
        categoryItemList4.add(CategoryItem(2, "category1"))
        categoryItemList4.add(CategoryItem(2, "category2"))
        categoryItemList4.add(CategoryItem(2, "category3"))
        categoryItemList4.add(CategoryItem(2, "category4"))
        categoryItemList4.add(CategoryItem(2, "category5"))

        val categoryItemList10: MutableList<CategoryItem> = ArrayList()
        categoryItemList4.add(CategoryItem(2, "category1"))
        categoryItemList4.add(CategoryItem(2, "category2"))
        categoryItemList4.add(CategoryItem(2, "category3"))
        categoryItemList4.add(CategoryItem(2, "category4"))
        categoryItemList4.add(CategoryItem(2, "category5"))


        val allCategory: MutableList<AllCategory> = ArrayList()
        allCategory.add(AllCategory("Category1", categoryItemList))
        allCategory.add(AllCategory("Category2", categoryItemList2))
        allCategory.add(AllCategory("Category3", categoryItemList3))
        allCategory.add(AllCategory("Category4", categoryItemList4))
        allCategory.add(AllCategory("Category4", categoryItemList5))
        allCategory.add(AllCategory("Category4", categoryItemList6))
        allCategory.add(AllCategory("Category4", categoryItemList7))
        allCategory.add(AllCategory("Category4", categoryItemList8))
        allCategory.add(AllCategory("Category4", categoryItemList9))
        allCategory.add(AllCategory("Category4", categoryItemList10))
//        allCategory.add(AllCategory("Category5", categoryItemList5))
//        allCategory.add(AllCategory("Category6", categoryItemList6))

        setMainCategoryRecycler(allCategory)
        super.onStart()
    }

    private fun setMainCategoryRecycler(allCategory: List<AllCategory>)
    {
//        mainCategoryRecycler = main_recycler
//        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity)
//        mainCategoryRecycler!!.layoutManager = layoutManager
//
//        mainRecyclerAdaptor = activity?.let { MainRecycleViewAdapter(it, allCategory) }
//        mainCategoryRecycler!!.adapter = mainRecyclerAdaptor

        main_recycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = MainRecycleViewAdapter(context,allCategory)
        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SearchFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}