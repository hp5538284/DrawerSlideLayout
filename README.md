# DrawerSlideLayout
支持二次拖拽展开

usage:

<?xml version="1.0" encoding="utf-8"?>
<com.yinghuanhang.slide.SlideReverseHorizonLayout
    android:id="@+id/base_slide"
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@android:color/holo_red_dark">
</com.yinghuanhang.slide.SlideReverseHorizonLayout>

mDrawer = (SlideDrawerHorizonLayout) findViewById(R.id.base_slide);

View content = inflater.inflate(R.layout.activity_main_content, null);
mDrawer.setContentView(content);

View menu = inflater.inflate(R.layout.activity_main_menu, null);
mDrawer.setSlideMenu(menu, 0.3f);
