# Permissions-Java
Permissions, shouldShowRequestPermissionRationale, requestPermissions, App Settings


## Permissiosn in the Manifest File:

```
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
    <uses-permission android:name="android.permission.CAMERA"/>
```

## Minimum sdk version in Gradle (Module:app):

```
    minSdkVersion 23
```

## Defining a Permission code:

```
    // Could be any number
    private static final int PERMISSION_CODE = 1001;
```

I use the following permission methods:

```
    * checkSelfPermission(String permission): 
    Checking the permissions {Manifest.permission.CAMERA or Manifest.permission.WRITE_EXTERNAL_STORAGE}
    
    * shouldShowRequestPermissionRationale (String permission): 
    Checking for permissions for first time.
    
    * requestPermissions(String[] permissions, int code): 
    List of permissions and code for being checking in permission result.
```

Finally, I check my PERMISSION_CODE in onRequestPermissionsResult:

```
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, 
                                               @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        
        switch (requestCode){
            case PERMISSION_CODE:
            .....
```

<p align="center">
<img src="images/01.png" width="250"> <img src="images/03.png" width="250"> 
<img src="images/04.png" width="250"> <img src="images/05.png" width="250"> <img src="images/06.png" width="250"> 
<img src="images/07.png" width="250">
</p>
