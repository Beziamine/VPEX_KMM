//
//  SplashView.swift
//  iosApp
//
//  Created by Amine Bezi on 25/5/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct SplashView: View {
    
    @State var isActive : Bool = false
        @State private var size = 0.8
        @State private var opacity = 0.5
        
        var body: some View {
            if isActive {
                ContentView()
            }
        }
        
}

struct SplashView_Previews: PreviewProvider {
    static var previews: some View {
        SplashView()
    }
}
