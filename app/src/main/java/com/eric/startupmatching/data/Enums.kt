package com.eric.startupmatching.data

enum class WorkStatus(val chineses_translation: String){
    Back("離開"),
    Leave("回來")
}

enum class EditTask(val message: String) {
    EnterMessage("輸入訊息...")
}

enum class ProjectStage(val stage: String) {
    Preparing("preparing"),
    Running("running"),
    Done("done")
}

enum class ViewPagerType(val type: String) {
    Task("任務"),
    Team("團隊")
}

enum class TodoStatus(val status: String) {
    Running("running"),
    Done("done")
}

enum class ChatRoomType(val type: String) {
    User("User"),
    Task("Task"),
    Team("Team")
}