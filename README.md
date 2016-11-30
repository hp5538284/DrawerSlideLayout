# DrawerSlideLayout
第一次滑动展开侧边栏，继续滑动展开全部内容
usage:
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
