# DrawerSlideLayout
支持二次拖拽展开

usage:
<p>
<com.yinghuanhang.slide.SlideReverseHorizonLayout
    android:id="@+id/base_slide"
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@android:color/holo_red_dark">
</com.yinghuanhang.slide.SlideReverseHorizonLayout>
</p>
<br>
mDrawer = (SlideDrawerHorizonLayout) findViewById(R.id.base_slide);
<br>
View content = inflater.inflate(R.layout.activity_main_content, null);
<br>
mDrawer.setContentView(content);

<br>
View menu = inflater.inflate(R.layout.activity_main_menu, null);
<br>
mDrawer.setSlideMenu(menu, 0.3f);
<br>
