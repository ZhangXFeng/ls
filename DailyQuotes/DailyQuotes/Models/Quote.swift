//
//  Quote.swift
//  DailyQuotes
//
//  名言数据模型
//

import Foundation

struct Quote: Identifiable, Codable {
    let id: UUID
    let content: String      // 名言内容
    let author: String       // 作者
    let category: String     // 分类

    init(id: UUID = UUID(), content: String, author: String, category: String = "励志") {
        self.id = id
        self.content = content
        self.author = author
        self.category = category
    }
}

// 名言分类
enum QuoteCategory: String, CaseIterable {
    case motivation = "励志"
    case wisdom = "智慧"
    case life = "人生"
    case success = "成功"
    case love = "爱情"
    case friendship = "友谊"
}
