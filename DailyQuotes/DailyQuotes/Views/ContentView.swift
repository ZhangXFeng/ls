//
//  ContentView.swift
//  DailyQuotes
//
//  主界面 - TabView导航
//

import SwiftUI

struct ContentView: View {
    @StateObject private var quoteData = QuoteData()

    var body: some View {
        TabView {
            TodayView()
                .tabItem {
                    Label("今日", systemImage: "sun.max.fill")
                }

            QuoteListView()
                .tabItem {
                    Label("名言库", systemImage: "book.fill")
                }

            FavoriteView()
                .tabItem {
                    Label("收藏", systemImage: "heart.fill")
                }
        }
        .environmentObject(quoteData)
        .accentColor(.orange)
    }
}

#Preview {
    ContentView()
}
