# @mat2718/face-detection

Live face detection for Capacitor

## Install

```bash
npm install @mat2718/face-detection
npx cap sync
```

## API

<docgen-index>

* [`startActiveScan(...)`](#startactivescan)
* [`readFaceFromImage(...)`](#readfacefromimage)
* [`stopScan()`](#stopscan)
* [`isSupported()`](#issupported)
* [`enableTorch()`](#enabletorch)
* [`disableTorch()`](#disabletorch)
* [`toggleTorch()`](#toggletorch)
* [`isTorchEnabled()`](#istorchenabled)
* [`isTorchAvailable()`](#istorchavailable)
* [`openSettings()`](#opensettings)
* [`checkPermissions()`](#checkpermissions)
* [`requestPermissions()`](#requestpermissions)
* [`addListener('faceScanned', ...)`](#addlistenerfacescanned)
* [`addListener('scanError', ...)`](#addlistenerscanerror)
* [`removeAllListeners()`](#removealllisteners)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)
* [Enums](#enums)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### startActiveScan(...)

```typescript
startActiveScan(options: StartActiveScanOptions) => Promise<void>
```

| Param         | Type                                                                      |
| ------------- | ------------------------------------------------------------------------- |
| **`options`** | <code><a href="#startactivescanoptions">StartActiveScanOptions</a></code> |

--------------------


### readFaceFromImage(...)

```typescript
readFaceFromImage(options: FaceProcessingOptions) => Promise<ProcessImageResult>
```

| Param         | Type                                                                    |
| ------------- | ----------------------------------------------------------------------- |
| **`options`** | <code><a href="#faceprocessingoptions">FaceProcessingOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#processimageresult">ProcessImageResult</a>&gt;</code>

--------------------


### stopScan()

```typescript
stopScan() => Promise<void>
```

--------------------


### isSupported()

```typescript
isSupported() => Promise<IsSupportedResult>
```

**Returns:** <code>Promise&lt;<a href="#issupportedresult">IsSupportedResult</a>&gt;</code>

--------------------


### enableTorch()

```typescript
enableTorch() => Promise<void>
```

--------------------


### disableTorch()

```typescript
disableTorch() => Promise<void>
```

--------------------


### toggleTorch()

```typescript
toggleTorch() => Promise<void>
```

--------------------


### isTorchEnabled()

```typescript
isTorchEnabled() => Promise<IsTorchEnabledResult>
```

**Returns:** <code>Promise&lt;<a href="#istorchenabledresult">IsTorchEnabledResult</a>&gt;</code>

--------------------


### isTorchAvailable()

```typescript
isTorchAvailable() => Promise<IsTorchAvailableResult>
```

**Returns:** <code>Promise&lt;<a href="#istorchavailableresult">IsTorchAvailableResult</a>&gt;</code>

--------------------


### openSettings()

```typescript
openSettings() => Promise<void>
```

--------------------


### checkPermissions()

```typescript
checkPermissions() => Promise<PermissionStatus>
```

**Returns:** <code>Promise&lt;<a href="#permissionstatus">PermissionStatus</a>&gt;</code>

--------------------


### requestPermissions()

```typescript
requestPermissions() => Promise<PermissionStatus>
```

**Returns:** <code>Promise&lt;<a href="#permissionstatus">PermissionStatus</a>&gt;</code>

--------------------


### addListener('faceScanned', ...)

```typescript
addListener(eventName: 'faceScanned', listenerFunc: (event: FaceScannedEvent) => void) => Promise<PluginListenerHandle> & PluginListenerHandle
```

| Param              | Type                                                                              |
| ------------------ | --------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'faceScanned'</code>                                                        |
| **`listenerFunc`** | <code>(event: <a href="#facescannedevent">FaceScannedEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt; & <a href="#pluginlistenerhandle">PluginListenerHandle</a></code>

--------------------


### addListener('scanError', ...)

```typescript
addListener(eventName: 'scanError', listenerFunc: (event: ScanErrorEvent) => void) => Promise<PluginListenerHandle> & PluginListenerHandle
```

| Param              | Type                                                                          |
| ------------------ | ----------------------------------------------------------------------------- |
| **`eventName`**    | <code>'scanError'</code>                                                      |
| **`listenerFunc`** | <code>(event: <a href="#scanerrorevent">ScanErrorEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt; & <a href="#pluginlistenerhandle">PluginListenerHandle</a></code>

--------------------


### removeAllListeners()

```typescript
removeAllListeners() => Promise<void>
```

--------------------


### Interfaces


#### StartActiveScanOptions

| Prop             | Type                                              |
| ---------------- | ------------------------------------------------- |
| **`lensFacing`** | <code><a href="#lensfacing">LensFacing</a></code> |


#### ProcessImageResult

| Prop        | Type                | Description         | Since |
| ----------- | ------------------- | ------------------- | ----- |
| **`faces`** | <code>Face[]</code> | The detected faces. | 5.1.0 |


#### Face

Represents a face detected by `FaceDetector`.

| Prop                          | Type                                  | Description                                                                                                                                                                | Since |
| ----------------------------- | ------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`bounds`**                  | <code><a href="#rect">Rect</a></code> | Returns the axis-aligned bounding rectangle of the detected face.                                                                                                          | 5.1.0 |
| **`landmarks`**               | <code>FaceLandmark[]</code>           | Returns a list of face landmarks.                                                                                                                                          | 5.1.0 |
| **`contours`**                | <code>FaceContour[]</code>            | Returns a list of face contours.                                                                                                                                           | 5.1.0 |
| **`trackingId`**              | <code>number</code>                   | Returns the tracking ID if the tracking is enabled.                                                                                                                        | 5.1.0 |
| **`headEulerAngleX`**         | <code>number</code>                   | Returns the rotation of the face about the horizontal axis of the image. Positive euler X is the face is looking up.                                                       | 5.1.0 |
| **`headEulerAngleY`**         | <code>number</code>                   | Returns the rotation of the face about the vertical axis of the image. Positive euler y is when the face turns toward the right side of the image that is being processed. | 5.1.0 |
| **`headEulerAngleZ`**         | <code>number</code>                   | Returns the rotation of the face about the axis pointing out of the image. Positive euler z is a counter-clockwise rotation within the image plane.                        | 5.1.0 |
| **`smilingProbability`**      | <code>number</code>                   | Returns a value between 0.0 and 1.0 giving a probability that the face is smiling.                                                                                         | 5.1.0 |
| **`leftEyeOpenProbability`**  | <code>number</code>                   | Returns a value between 0.0 and 1.0 giving a probability that the face's left eye is open.                                                                                 | 5.1.0 |
| **`rightEyeOpenProbability`** | <code>number</code>                   | Returns a value between 0.0 and 1.0 giving a probability that the face's right eye is open.                                                                                | 5.1.0 |


#### Rect

<a href="#rect">Rect</a> holds four integer coordinates for a rectangle.

| Prop         | Type                | Description                                          | Since |
| ------------ | ------------------- | ---------------------------------------------------- | ----- |
| **`left`**   | <code>number</code> | The X coordinate of the left side of the rectangle.  | 5.1.0 |
| **`top`**    | <code>number</code> | The Y coordinate of the top of the rectangle.        | 5.1.0 |
| **`right`**  | <code>number</code> | The X coordinate of the right side of the rectangle. | 5.1.0 |
| **`bottom`** | <code>number</code> | The Y coordinate of the bottom of the rectangle.     | 5.1.0 |


#### FaceLandmark

Represent a face landmark.
A landmark is a point on a detected face, such as an eye, nose, or mouth.

| Prop           | Type                                                  | Description                                                                                      | Since |
| -------------- | ----------------------------------------------------- | ------------------------------------------------------------------------------------------------ | ----- |
| **`type`**     | <code><a href="#landmarktype">LandmarkType</a></code> | Gets the <a href="#facelandmark">FaceLandmark</a>.<a href="#landmarktype">LandmarkType</a> type. | 5.1.0 |
| **`position`** | <code><a href="#point">Point</a></code>               | Gets a 2D point for landmark position, where (0, 0) is the upper-left corner of the image.       | 5.1.0 |


#### Point

<a href="#point">Point</a> holds two coordinates

| Prop    | Type                |
| ------- | ------------------- |
| **`x`** | <code>number</code> |
| **`y`** | <code>number</code> |


#### FaceContour

Represent a face contour.
A contour is a list of points on a detected face, such as the mouth.

| Prop         | Type                                                | Description                                                                                         | Since |
| ------------ | --------------------------------------------------- | --------------------------------------------------------------------------------------------------- | ----- |
| **`type`**   | <code><a href="#contourtype">ContourType</a></code> | Gets the <a href="#facecontour">FaceContour</a>.<a href="#contourtype">ContourType</a> type.        | 5.1.0 |
| **`points`** | <code>Point[]</code>                                | Gets a list of 2D points for this face contour, where (0, 0) is the upper-left corner of the image. | 5.1.0 |


#### FaceProcessingOptions

| Prop                     | Type                                                              | Description                                                                                                                                                                                     | Default                              | Since |
| ------------------------ | ----------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------------------------ | ----- |
| **`performanceMode`**    | <code><a href="#performancemode">PerformanceMode</a></code>       | Defines options to control accuracy / speed trade-offs in performing face detection.                                                                                                            | <code>PerformanceMode.Fast</code>    | 5.1.0 |
| **`landmarkMode`**       | <code><a href="#landmarkmode">LandmarkMode</a></code>             | Defines options to enable face landmarks or not.                                                                                                                                                | <code>LandmarkMode.None</code>       | 5.1.0 |
| **`contourMode`**        | <code><a href="#contourmode">ContourMode</a></code>               | Defines options to enable face contours or not.                                                                                                                                                 | <code>ContourMode.None</code>        | 5.1.0 |
| **`classificationMode`** | <code><a href="#classificationmode">ClassificationMode</a></code> | Defines options for characterizing attributes such as "smiling" * and "eyes open".                                                                                                              | <code>ClassificationMode.None</code> | 5.1.0 |
| **`minFaceSize`**        | <code>number</code>                                               | Sets the smallest desired face size, expressed as a proportion of the width of the head to the image width.                                                                                     | <code>0.1</code>                     | 5.1.0 |
| **`enableTracking`**     | <code>boolean</code>                                              | Enables face tracking, which will maintain a consistent ID for each face when processing consecutive frames. Tracking should be disabled for handling a series of non-consecutive still images. | <code>false</code>                   | 5.1.0 |


#### IsSupportedResult

| Prop            | Type                 | Description                                                                             | Since |
| --------------- | -------------------- | --------------------------------------------------------------------------------------- | ----- |
| **`supported`** | <code>boolean</code> | Whether or not the barcode scanner is supported by checking if the device has a camera. | 0.0.1 |


#### IsTorchEnabledResult

| Prop          | Type                 | Description                          | Since |
| ------------- | -------------------- | ------------------------------------ | ----- |
| **`enabled`** | <code>boolean</code> | Whether or not the torch is enabled. | 0.0.1 |


#### IsTorchAvailableResult

| Prop            | Type                 | Description                            | Since |
| --------------- | -------------------- | -------------------------------------- | ----- |
| **`available`** | <code>boolean</code> | Whether or not the torch is available. | 0.0.1 |


#### PermissionStatus

| Prop         | Type                                                                    | Since |
| ------------ | ----------------------------------------------------------------------- | ----- |
| **`camera`** | <code><a href="#camerapermissionstate">CameraPermissionState</a></code> | 0.0.1 |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


#### FaceScannedEvent

| Prop          | Type                 | Description         | Since |
| ------------- | -------------------- | ------------------- | ----- |
| **`barcode`** | <code>Barcode</code> | A detected barcode. | 0.0.1 |


#### ScanErrorEvent

| Prop          | Type                | Description        | Since |
| ------------- | ------------------- | ------------------ | ----- |
| **`message`** | <code>string</code> | The error message. | 0.0.1 |


### Type Aliases


#### CameraPermissionState

<code><a href="#permissionstate">PermissionState</a> | 'limited'</code>


#### PermissionState

<code>'prompt' | 'prompt-with-rationale' | 'granted' | 'denied'</code>


### Enums


#### LensFacing

| Members     | Value                | Since |
| ----------- | -------------------- | ----- |
| **`Front`** | <code>'FRONT'</code> | 0.0.1 |
| **`Back`**  | <code>'BACK'</code>  | 0.0.1 |


#### LandmarkType

| Members           | Value           | Description                                                                                                                                                                                                            | Since |
| ----------------- | --------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`MouthBottom`** | <code>0</code>  | The center of the subject's bottom lip.                                                                                                                                                                                | 5.1.0 |
| **`LeftCheek`**   | <code>1</code>  | The midpoint between the subject's left mouth corner and the outer corner of the subject's left eye. For full profile faces, this becomes the centroid of the nose base, nose tip, left ear lobe and left ear tip.     | 5.1.0 |
| **`LeftEar`**     | <code>3</code>  | The midpoint of the subject's left ear tip and left ear lobe.                                                                                                                                                          | 5.1.0 |
| **`LeftEye`**     | <code>4</code>  | The center of the subject's left eye cavity.                                                                                                                                                                           | 5.1.0 |
| **`MouthLeft`**   | <code>5</code>  | The subject's left mouth corner where the lips meet.                                                                                                                                                                   | 5.1.0 |
| **`NoseBase`**    | <code>6</code>  | The midpoint between the subject's nostrils where the nose meets the face.                                                                                                                                             | 5.1.0 |
| **`RightCheek`**  | <code>7</code>  | The midpoint between the subject's right mouth corner and the outer corner of the subject's right eye. For full profile faces, this becomes the centroid of the nose base, nose tip, right ear lobe and right ear tip. | 5.1.0 |
| **`RightEar`**    | <code>9</code>  | The midpoint of the subject's right ear tip and right ear lobe.                                                                                                                                                        | 5.1.0 |
| **`RightEye`**    | <code>10</code> | The center of the subject's right eye cavity.                                                                                                                                                                          | 5.1.0 |
| **`MouthRight`**  | <code>11</code> | The subject's right mouth corner where the lips meet.                                                                                                                                                                  | 5.1.0 |


#### ContourType

| Members                  | Value           | Description                                        | Since |
| ------------------------ | --------------- | -------------------------------------------------- | ----- |
| **`Face`**               | <code>1</code>  | The outline of the subject's face.                 | 5.1.0 |
| **`LeftEyebrowTop`**     | <code>2</code>  | The top outline of the subject's left eyebrow.     | 5.1.0 |
| **`LeftEyebrowBottom`**  | <code>3</code>  | The bottom outline of the subject's left eyebrow.  | 5.1.0 |
| **`RightEyebrowTop`**    | <code>4</code>  | The top outline of the subject's right eyebrow.    | 5.1.0 |
| **`RightEyebrowBottom`** | <code>5</code>  | The bottom outline of the subject's right eyebrow. | 5.1.0 |
| **`LeftEye`**            | <code>6</code>  | The outline of the subject's left eye cavity.      | 5.1.0 |
| **`RightEye`**           | <code>7</code>  | The outline of the subject's right eye cavity.     | 5.1.0 |
| **`UpperLipTop`**        | <code>8</code>  | The top outline of the subject's upper lip.        | 5.1.0 |
| **`UpperLipBottom`**     | <code>9</code>  | The bottom outline of the subject's upper lip.     | 5.1.0 |
| **`LowerLipTop`**        | <code>10</code> | The top outline of the subject's lower lip.        | 5.1.0 |
| **`LowerLipBottom`**     | <code>11</code> | The bottom outline of the subject's lower lip.     | 5.1.0 |
| **`NoseBridge`**         | <code>12</code> | The outline of the subject's nose bridge.          | 5.1.0 |
| **`NoseBottom`**         | <code>13</code> | The outline of the subject's nose bridge.          | 5.1.0 |
| **`LeftCheek`**          | <code>14</code> | The center of the left cheek.                      | 5.1.0 |
| **`RightCheek`**         | <code>15</code> | The center of the right cheek.                     | 5.1.0 |


#### PerformanceMode

| Members        | Value          | Description                                                                                                                                                                                                                 | Since |
| -------------- | -------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`Fast`**     | <code>1</code> | Indicates a preference for speed in the options that may make an accuracy vs. speed trade-off. This will tend to detect fewer faces and may be less precise in determining values such as position, but will run faster.    | 5.1.0 |
| **`Accurate`** | <code>2</code> | Indicates a preference for accuracy in the options that may make an accuracy vs. speed trade-off. This will tend to detect more faces and may be more precise in determining values such as position, at the cost of speed. | 5.1.0 |


#### LandmarkMode

| Members    | Value          | Description                                                        | Since |
| ---------- | -------------- | ------------------------------------------------------------------ | ----- |
| **`None`** | <code>1</code> | Does not perform landmark detection.                               | 5.1.0 |
| **`All`**  | <code>2</code> | Detects <a href="#facelandmark">FaceLandmark</a> for a given face. | 5.1.0 |


#### ContourMode

| Members    | Value          | Description                                                                                                           | Since |
| ---------- | -------------- | --------------------------------------------------------------------------------------------------------------------- | ----- |
| **`None`** | <code>1</code> | Does not perform contour detection.                                                                                   | 5.1.0 |
| **`All`**  | <code>2</code> | Detects <a href="#facecontour">FaceContour</a> for a given face. Note that it would return contours for up to 5 faces | 5.1.0 |


#### ClassificationMode

| Members    | Value          | Description                                        | Since |
| ---------- | -------------- | -------------------------------------------------- | ----- |
| **`None`** | <code>1</code> | Does not perform classification.                   | 5.1.0 |
| **`All`**  | <code>2</code> | Performs "eyes open" and "smiling" classification. | 5.1.0 |

</docgen-api>
