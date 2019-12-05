/*
 * Copyright (C) 2014 - 2019 | Wurst-Imperium | All rights reserved.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */
package net.mersid.standardhud.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.mersid.standardhud.events.OnRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;

@Mixin(InGameHud.class)
public class IngameHudMixin extends DrawableHelper
{
	@Inject(at = {@At(value = "INVOKE",
			target = "Lcom/mojang/blaze3d/systems/RenderSystem;enableBlend()V",
			ordinal = 4)}, method = {"render(F)V"})
	private void onRender(float partialTicks, CallbackInfo ci)
	{
		if(MinecraftClient.getInstance().options.debugEnabled)
			return;
		
		OnRenderCallback.EVENT.invoker().onRender();
	}
	
	@Inject(at = @At("HEAD"), method = { "renderStatusEffectOverlay()V" }, cancellable = true)
	private void onRenderStatusEffectOverlay(CallbackInfo ci)
	{
		ci.cancel();
	}
}