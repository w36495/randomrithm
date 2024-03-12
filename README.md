# 🎰 랜덤리즘
백준 사이트의 알고리즘 문제들을 정답비율과 레벨로 편식하는 편식러를 위해 만든 **랜덤으로 알고리즘 문제를 보여주는 서비스**  
</br>
`solved.ac` 의 비공식 API 사용 ==> [API 사이트로 이동](https://solvedac.github.io/unofficial-documentation/#/)

## 배포
[playstore 이동](https://play.google.com/store/apps/details?id=com.w36495.randomrithm)
- v1.0 (2024.02.21)
- v1.1 (2024.02.26)

</br>

## 기능
### 기본 기능
|**`레벨별 랜덤 문제`**|**`알고리즘별 랜덤 문제(전체 레벨)`**|**`출처별 랜덤 문제`**|
|:--:|:--:|:--:|
|![레벨별-랜덤-문제](https://github.com/w36495/randomrithm/assets/52291662/1a79bb2f-9a73-4137-8b35-24a33392a65d)|![전체-레벨-선택](https://github.com/w36495/randomrithm/assets/52291662/fc107bdf-bd8e-4971-97fb-641df8a5f38e)|![출처별-랜덤-문제](https://github.com/w36495/randomrithm/assets/52291662/b4efaa88-dedb-4aad-a194-c563e21946ac)

</br>

### 상세 기능
|**`알고리즘별 랜덤 문제(특정 레벨)`**|**`특정 레벨의 문제가 존재하지 않는 경우`**|**`관련 알고리즘을 통한 문제`**|
|:--:|:--:|:--:|
|![특정-레벨-선택](https://github.com/w36495/randomrithm/assets/52291662/e5a3ba65-165f-4c39-bfc3-6c92dc41db99)|![특정-레벨의-문제가-존재하지-않는-경우](https://github.com/w36495/randomrithm/assets/52291662/f75955d8-e6ef-428b-a566-a403123d8873)|![관련-알고리즘-랜덤-문제](https://github.com/w36495/randomrithm/assets/52291662/3059e32b-39ec-4a64-b72d-ed852d867887)|


</br>

## 🛠️ 사용 기술
|Title|Contents|
|--|--|
|Language|Kotlin|
|App Architecture|Layered Architecture, MVVM|
|Design Pattern|Repository Pattern, Adapter Pattern, Observer Pattern|
|Android Architecture Component|LiveData, ViewModel|
|Asynchronous Processing|Coroutine|
|Network|Retrofit2, okhttp3, Moshi|
|Dependency Injection|Hilt|
|Other Tool|Figma|

</br>

## 💥 Trouble Shooting
### 1️⃣ 기존에 선택했던 메뉴를 클릭한 후, 다른 메뉴를 클릭했을 때 화면에 문제가 보이지 않음
ViewModelProvider 을 통해 VIewModel 의 객체를 생성하는 과정에서 ViewModelStoreOwner 를 LiveData 를 관찰하는 Fragment 가 아닌 해당  Fragment 와 연결되어 있는 FragmentActivity 로 연결되어있음을 확인하였다.  
LiveData 를 관찰하고 있는 Fragment 를 ViewModelStoreOwner 로 세팅하여 해결!  
[--> 자세한 문제 해결 과정](https://w36495.tistory.com/105)
### 2️⃣ 네트워크 통신 URL 에 콜론(:), 더하기(+) 와 같은 문자가 포함되어 있는 문제
데이터를 요청하는 주소 URL 을 확인하기 위해 okhttp3 의 LoggingInterceptor 를 사용하여 전달되는 URL 의 쿼리가 ‘%3A’, ‘%2B’ 와 같은 문자열로 인코딩 됨을 확인하였고, @Query 의 필드인 encoded() 를 true 로 설정해주었다.  
[--> 자세한 문제 해결 과정](https://w36495.tistory.com/104)
