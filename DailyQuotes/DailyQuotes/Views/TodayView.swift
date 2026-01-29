//
//  TodayView.swift
//  DailyQuotes
//
//  今日名言页面
//

import SwiftUI

struct TodayView: View {
    @EnvironmentObject var quoteData: QuoteData
    @State private var isAnimating = false
    @State private var showShareSheet = false

    var body: some View {
        NavigationView {
            ZStack {
                // 背景渐变
                LinearGradient(
                    gradient: Gradient(colors: [
                        Color.orange.opacity(0.3),
                        Color.yellow.opacity(0.2),
                        Color.white
                    ]),
                    startPoint: .topLeading,
                    endPoint: .bottomTrailing
                )
                .ignoresSafeArea()

                VStack(spacing: 30) {
                    Spacer()

                    // 日期显示
                    Text(formattedDate())
                        .font(.subheadline)
                        .foregroundColor(.secondary)

                    // 今日名言卡片
                    if let quote = quoteData.todayQuote {
                        QuoteCardView(quote: quote, isLarge: true)
                            .scaleEffect(isAnimating ? 1.0 : 0.9)
                            .opacity(isAnimating ? 1.0 : 0.0)
                    }

                    Spacer()

                    // 操作按钮
                    HStack(spacing: 40) {
                        // 收藏按钮
                        Button(action: {
                            if let quote = quoteData.todayQuote {
                                withAnimation(.spring()) {
                                    quoteData.toggleFavorite(quote)
                                }
                            }
                        }) {
                            VStack {
                                Image(systemName: isFavorite ? "heart.fill" : "heart")
                                    .font(.title)
                                    .foregroundColor(isFavorite ? .red : .gray)
                                Text("收藏")
                                    .font(.caption)
                                    .foregroundColor(.secondary)
                            }
                        }

                        // 分享按钮
                        Button(action: {
                            showShareSheet = true
                        }) {
                            VStack {
                                Image(systemName: "square.and.arrow.up")
                                    .font(.title)
                                    .foregroundColor(.blue)
                                Text("分享")
                                    .font(.caption)
                                    .foregroundColor(.secondary)
                            }
                        }

                        // 刷新按钮
                        Button(action: {
                            withAnimation(.spring()) {
                                quoteData.todayQuote = quoteData.getRandomQuote()
                            }
                        }) {
                            VStack {
                                Image(systemName: "arrow.clockwise")
                                    .font(.title)
                                    .foregroundColor(.green)
                                Text("换一条")
                                    .font(.caption)
                                    .foregroundColor(.secondary)
                            }
                        }
                    }
                    .padding(.bottom, 50)
                }
                .padding()
            }
            .navigationTitle("每日名言")
            .onAppear {
                withAnimation(.easeOut(duration: 0.6)) {
                    isAnimating = true
                }
            }
            .sheet(isPresented: $showShareSheet) {
                if let quote = quoteData.todayQuote {
                    ShareSheet(items: ["\(quote.content)\n\n—— \(quote.author)"])
                }
            }
        }
    }

    private var isFavorite: Bool {
        guard let quote = quoteData.todayQuote else { return false }
        return quoteData.isFavorite(quote)
    }

    private func formattedDate() -> String {
        let formatter = DateFormatter()
        formatter.locale = Locale(identifier: "zh_CN")
        formatter.dateFormat = "yyyy年M月d日 EEEE"
        return formatter.string(from: Date())
    }
}

// 分享Sheet
struct ShareSheet: UIViewControllerRepresentable {
    let items: [Any]

    func makeUIViewController(context: Context) -> UIActivityViewController {
        UIActivityViewController(activityItems: items, applicationActivities: nil)
    }

    func updateUIViewController(_ uiViewController: UIActivityViewController, context: Context) {}
}

#Preview {
    TodayView()
        .environmentObject(QuoteData())
}
