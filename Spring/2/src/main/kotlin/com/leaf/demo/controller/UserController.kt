package com.leaf.demo.controller

import com.leaf.demo.entity.UserInfo
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.web.bind.annotation.*
import javax.annotation.PostConstruct

@RestController
@EnableAutoConfiguration(exclude = [DataSourceAutoConfiguration::class])
class UserController {

    private val userMap: MutableMap<String, UserInfo> = mutableMapOf()
    
    @PostConstruct
    fun init() {
        userMap["1"] = UserInfo("1", "임리프", "111-1111", "광주")
        userMap["2"] = UserInfo("2", "리프", "222-2222", "서울")
        userMap["3"] = UserInfo("3", "임태", "333-3333", "판교")
    }

    @GetMapping(path = ["/user/{id}"])
    fun getUserInfo(@PathVariable("id") id: String) = userMap[id]

    @GetMapping(path = ["user/all"])
    fun getUserInfoAll() = ArrayList<UserInfo>(userMap.values)

    @PutMapping(path = ["/user/{id}"])
    fun putUserInfo(@PathVariable("id") id: String, @RequestParam("name") name: String, @RequestParam("phone") phone: String, @RequestParam("address") address: String) {

        val userInfo = UserInfo(id, name, phone, address)
        userMap[id] = userInfo
    }

    @PostMapping(path = ["/user/{id}"])
    fun postUserInfo(@PathVariable("id") id: String, @RequestParam("name") name: String, @RequestParam("phone") phone: String, @RequestParam("address") address: String) {

        val userInfo = userMap[id]
        userInfo?.name = name
        userInfo?.phone = phone
        userInfo?.address = address
    }

    @DeleteMapping(path = ["/user/{id}"])
    fun deleteUserInfo(@PathVariable("id") id: String) = userMap.remove(id)
}