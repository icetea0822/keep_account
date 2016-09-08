package com.shengzidong.keepacounts.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.shengzidong.keepacounts.R;
import com.shengzidong.keepacounts.entity.Category;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Administrator on 2016/8/31.
 */
public class MyCategory {
    Context context;
    private SharedPreferences sharedPreferences;

    public MyCategory(Context context) {
        this.context = context;
    }

    public void setCategoryList(String addCategory, String removeCategory) {
//        SQLiteDatabase db = context.openOrCreateDatabase("account", Context.MODE_PRIVATE, null);
//        db.execSQL("DROP TABLE IF EXISTS category");
//        db.execSQL("CREATE TABLE category (_id INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR)");

        sharedPreferences = context.getSharedPreferences("category", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Set<String> categories = new TreeSet<>();
        categories.add("收入");
        categories.add("在外吃饭");
        categories.add("娱乐");
        categories.add("食品");
        categories.add("饮料");
        categories.add("日用品");
        categories.add("公交/地铁");
        categories.add("停车费");
        categories.add("出租车");
        categories.add("汽油");
        categories.add("交际费");
        categories.add("房租");
        categories.add("电视费");
        categories.add("水费");
        categories.add("燃气费");
        categories.add("宽带费");
        categories.add("医院");
        categories.add("理发");
        categories.add("宠物用品");
        categories.add("养车费");
        categories.add("保险费");
        categories.add("家居");
        categories.add("罚单");
        categories.add("过路费");
        categories.add("服装");
        categories.add("医疗");
        categories.add("电子产品");
        categories.add("教育");
        if (addCategory != null) {
            categories.add(addCategory);
        }
        if (removeCategory != null && categories.contains(removeCategory)) {
            categories.remove(removeCategory);
        }


        editor.putStringSet("categories", categories);
        editor.commit();
    }


    public ArrayList<Category> getCategoryList() {
        Set<String> categories;
        ArrayList<Category> categoryList = new ArrayList<>();
        sharedPreferences = context.getSharedPreferences("category", Context.MODE_PRIVATE);
        categories = sharedPreferences.getStringSet("categories", null);

        Iterator iterator = categories.iterator();
        Category category ;
        while (iterator.hasNext()) {
            String categoryName = (String) iterator.next();

            switch (categoryName) {
                case "收入":
                    category = new Category();
                    category.setName(categoryName);
                    category.setId(0);
                    category.setIcon(R.drawable.icon_taxi);
                    break;
                case "在外吃饭":
                    category = new Category();
                    category.setName(categoryName);
                    category.setId(1);
                    category.setIcon(R.drawable.icon_clothes);
                    break;
                case "娱乐":
                    category = new Category();
                    category.setName(categoryName);
                    category.setId(2);
                    category.setIcon(R.drawable.icon_arrowhead);
                    break;
                case "食品":
                    category = new Category();
                    category.setName(categoryName);
                    category.setId(3);
                    category.setIcon(R.drawable.icon_arrowhead);
                    break;
                case "饮料":
                    category = new Category();
                    category.setName(categoryName);
                    category.setId(4);
                    category.setIcon(R.drawable.icon_arrowhead);
                    break;
                case "日用品":
                    category = new Category();
                    category.setName(categoryName);
                    category.setId(5);
                    category.setIcon(R.drawable.icon_arrowhead);
                    break;
                case "公交/地铁":
                    category = new Category();
                    category.setName(categoryName);
                    category.setId(6);
                    category.setIcon(R.drawable.icon_arrowhead);
                    break;
                case "停车费":
                    category = new Category();
                    category.setName(categoryName);
                    category.setId(7);
                    category.setIcon(R.drawable.icon_arrowhead);
                    break;
                case "出租车":
                    category = new Category();
                    category.setName(categoryName);
                    category.setId(8);
                    category.setIcon(R.drawable.icon_arrowhead);
                    break;
                case "汽油":
                    category = new Category();
                    category.setName(categoryName);
                    category.setId(9);
                    category.setIcon(R.drawable.icon_arrowhead);
                    break;
                case "交际费":
                    category = new Category();
                    category.setName(categoryName);
                    category.setId(10);
                    category.setIcon(R.drawable.icon_arrowhead);
                    break;
                case "房租":
                    category = new Category();
                    category.setName(categoryName);
                    category.setId(11);
                    category.setIcon(R.drawable.icon_arrowhead);
                    break;
                case "按揭":
                    category = new Category();
                    category.setName(categoryName);
                    category.setId(12);
                    category.setIcon(R.drawable.icon_arrowhead);
                    break;
                case "电视费":
                    category = new Category();
                    category.setName(categoryName);
                    category.setId(13);
                    category.setIcon(R.drawable.icon_arrowhead);
                    break;
                case "电费":
                    category = new Category();
                    category.setName(categoryName);
                    category.setId(14);
                    category.setIcon(R.drawable.icon_arrowhead);
                    break;
                case "水费":
                    category = new Category();
                    category.setName(categoryName);
                    category.setId(15);
                    category.setIcon(R.drawable.icon_arrowhead);
                    break;
                case "燃气费":
                    category = new Category();
                    category.setName(categoryName);
                    category.setId(16);
                    category.setIcon(R.drawable.icon_arrowhead);
                    break;
                case "宽带费":
                    category = new Category();
                    category.setName(categoryName);
                    category.setId(17);
                    category.setIcon(R.drawable.icon_arrowhead);
                    break;
                case "理发":
                    category = new Category();
                    category.setName(categoryName);
                    category.setId(18);
                    category.setIcon(R.drawable.icon_arrowhead);
                    break;
                case "宠物用品":
                    category = new Category();
                    category.setName(categoryName);
                    category.setId(19);
                    category.setIcon(R.drawable.icon_arrowhead);
                    break;
                case "养车费":
                    category = new Category();
                    category.setName(categoryName);
                    category.setId(20);
                    category.setIcon(R.drawable.icon_arrowhead);
                    break;
                case "保险费":
                    category = new Category();
                    category.setName(categoryName);
                    category.setId(21);
                    category.setIcon(R.drawable.icon_arrowhead);
                    break;
                case "家居":
                    category = new Category();
                    category.setName(categoryName);
                    category.setId(22);
                    category.setIcon(R.drawable.icon_arrowhead);
                    break;
                case "罚单":
                    category = new Category();
                    category.setName(categoryName);
                    category.setId(23);
                    category.setIcon(R.drawable.icon_arrowhead);
                    break;
                case "过路费":
                    category = new Category();
                    category.setName(categoryName);
                    category.setId(24);
                    category.setIcon(R.drawable.icon_arrowhead);
                    break;
                case "服装":
                    category = new Category();
                    category.setName(categoryName);
                    category.setId(25);
                    category.setIcon(R.drawable.icon_arrowhead);
                    break;
                case "医疗":
                    category = new Category();
                    category.setName(categoryName);
                    category.setId(26);
                    category.setIcon(R.drawable.icon_arrowhead);
                    break;
                case "电子产品":
                    category = new Category();
                    category.setName(categoryName);
                    category.setId(27);
                    category.setIcon(R.drawable.icon_arrowhead);
                    break;
                case "教育":
                    category = new Category();
                    category.setName(categoryName);
                    category.setId(28);
                    category.setIcon(R.drawable.icon_arrowhead);
                    break;
                default:
                    category = new Category();
                    category.setName(categoryName);
                    category.setId(28);
                    category.setIcon(R.drawable.icon_arrowhead);
                    break;
            }
            categoryList.add(category);

        }

        return categoryList;
    }


}
