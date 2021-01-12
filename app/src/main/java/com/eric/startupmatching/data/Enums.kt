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
    User("Private"),
    Task("Task"),
    Team("Team")
}

enum class FollowStatus(val type: String) {
    Follow("+關注"),
    Followed("已關注")
}

enum class EditTodoDescriptionBtn(val type: String) {
    Edit("更動"),
    Confirm("確認")
}

enum class FragmentName(val type: String) {
    SocialMediaFragment("socialMediaFragment"),
    ProjectMainFragment("projectMainFragment"),
    PeopleMainFragment("peopleMainFragment"),
    ChatRoomMainFragment("chatRoomMainFragment"),
    ProfileMainFragment("profileMainFragment")
}

enum class ProfileViewpagerCatagory(val catagory: String) {
    FollowingList("關注清單"),

}

enum class ToolBarText(val type: String) {
    ProfileMain("Profile"),

}