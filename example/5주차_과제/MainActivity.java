<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/main"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    tools:context=".MainActivity">

    <View
        android:layout_width="150dp"
        android:layout_height="150dp"
        android:id="@+id/base_widget"
        android:layout_centerInParent="true"
        android:background="@android:color/holo_green_light"/>

    <Button
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:id="@+id/btn_1"
        android:text="1번"
        android:layout_alignTop="@+id/base_widget"/>

    <Button
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:id="@+id/btn_2"
        android:text="2번"
        android:layout_alignBottom="@+id/base_widget"/>

    <Button
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:id="@+id/btn_3"
        android:text="3번"
        android:layout_toStartOf="@+id/base_widget"
        android:layout_alignParentBottom="true"/>


    <Button
        android:id="@+id/btn_4"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignStart="@+id/base_widget"
        android:text="4번" />

    <Button
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:id="@+id/btn_5"
        android:text="5번"
        android:layout_alignEnd="@+id/base_widget"
        android:layout_alignParentBottom="true"/>

    <Button
        android:id="@+id/btn_6"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_above="@+id/base_widget"
        android:layout_alignParentEnd="true"
        android:text="6번" />

</RelativeLayout>
