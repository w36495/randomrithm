package com.w36495.randomrithm.utils

enum class Constants(val message: String) {
    LOGIN_SUCCESS("로그인에 성공하였습니다."),
    LOGIN_EXIST_ACCOUNT("사용가능한 아이디입니다."),
    LOGIN_NOT_EXIST_ACCOUNT("존재하지 않는 아이디입니다."),
    LOGIN_SUGGESTION_CHECK_ACCOUNT("계정 확인을 해주세요."),
    LOGIN_SUGGESTION_INPUT_ACCOUNT("올바른 아이디를 입력해주세요."),

    EXCEPTION_WRONG_INPUT("잘못된 입력입니다. 다시 입력해주세요."),
    EXCEPTION_NOT_EXIST_PROBLEM("문제가 존재하지 않습니다."),
    EXCEPTION_DATA_ROAD_FAILED("데이터를 불러오는데 실패하였습니다."),
    EXCEPTION_NOT_EXIST_USER("잘못된 사용자 정보입니다."),
    EXCEPTION_WRONG_ACCESS("잘못된 접근입니다."),
}